package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.booking.BookingInputDto;
import ru.practicum.dto.booking.BookingOutputDto;
import ru.practicum.model.booking.Booking;
import ru.practicum.model.booking.BookingStatus;
import ru.practicum.model.item.Item;
import ru.practicum.model.user.User;
import ru.practicum.exception.*;
import ru.practicum.mapper.booking.BookingMapper;
import ru.practicum.paging.item.CustomPageRequest;
import ru.practicum.shareit.booking.state.BookingState;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceDb implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public BookingOutputDto createBooking(BookingInputDto bookingInputDto, Long userId) {
        isTheTimeCorrect(bookingInputDto);

        Item item = getItem(bookingInputDto.getItemId());

        if (!item.getAvailable()) {
            throw new ItemUnavalibleException("Вещь снята с аренды");
        }
        if (userId.equals(item.getOwner().getId())) {
            throw new OtherDataException("Пользователь не может забронировать свою вещь");
        }

        User user = getUser(userId);
        Booking booking = new Booking();
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStart(bookingInputDto.getStart());
        booking.setEnd(bookingInputDto.getEnd());
        booking.setStatus(BookingStatus.WAITING);

        Booking savedBooking = bookingRepository.save(booking);

        log.info("Бронирование создано с ID: {}", savedBooking.getId());

        return BookingMapper.toBookingDto(savedBooking);
    }

    @Override
    @Transactional
    public BookingOutputDto updateApprove(Long bookingId, Boolean approved, Long userId) {

        Booking booking = getBooking(bookingId);
        User user = getUser(userId);
        User owner = booking.getItem().getOwner();
        if (!user.equals(owner)) {
            throw new OtherDataException("Статус бронирования может изменить только владелец");
        }
        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new InvalidRequestException("Статус бронирования можно изменить только во время его ожидания");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        Booking savedBooking = bookingRepository.save(booking);

        log.info("Бронирование изменено с ID: {}", savedBooking.getId());
        return BookingMapper.toBookingDto(savedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingOutputDto getBookingInfo(Long bookingId, Long userId) {
        log.info("Получен запрос на получение информации о бронировании  с ID: {}", bookingId);

        User user = getUser(userId);
        Booking booking = getBooking(bookingId);
        User owner = booking.getItem().getOwner();
        User booker = booking.getBooker();
        boolean canGetInfo = owner.equals(user) || booker.equals(user);
        if (!canGetInfo) {
            throw new OtherDataException("Просматривать информацию о бронировании могут владелец или бронирующий");
        }

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingOutputDto> getAllBookings(Long bookerId, BookingState state, Integer from, Integer size) {
        User booker = getUser(bookerId);
        log.info("Получен запрос на получение списка бронирований");

        Map<BookingState, Supplier<List<Booking>>> stateToMethodMap = Map.of(
                BookingState.ALL, () -> bookingRepository.findByBooker(booker),
                BookingState.CURRENT, () -> bookingRepository.findCurrentByBooker(booker),
                BookingState.PAST, () -> bookingRepository.findByBookerAndEndIsBefore(booker, LocalDateTime.now()),
                BookingState.FUTURE, () -> bookingRepository.findByBookerAndStartIsAfter(booker, LocalDateTime.now()),
                BookingState.WAITING, () -> bookingRepository.findByBookerAndStatus(booker, BookingStatus.WAITING),
                BookingState.REJECTED, () -> bookingRepository.findByBookerAndStatus(booker, BookingStatus.REJECTED)
        );

        Supplier<List<Booking>> supplier = stateToMethodMap.get(state);
        if (supplier == null) {
            throw new InvalidRequestException("Не существующий статус: " + state);
        }

        return BookingMapper.convertBookings(supplier.get());
    }


    @Override
    @Transactional(readOnly = true)
    public List<BookingOutputDto> getAllBookingsForOwner(Long ownerId, BookingState state, Integer from, Integer size) {
        User owner = getUser(ownerId);
        Pageable pageable = CustomPageRequest.create(from, size, Sort.by(Sort.Direction.DESC, "start"));
        List<Item> items = itemRepository.findByOwner(owner, pageable);
        List<Long> itemIds = items.stream().map(Item::getId).toList();
        log.info("Получен запрос на получение списка бронирований по владельцу");
        return getAllBookingsForItem(itemIds, state);
    }

    private List<BookingOutputDto> getAllBookingsForItem(List<Long> itemIds, BookingState state) {
        Map<BookingState, Supplier<List<Booking>>> stateToMethodMap = Map.of(
                BookingState.ALL, () -> bookingRepository.findAllByItemIds(itemIds),
                BookingState.CURRENT, () -> bookingRepository.findCurrentByItemIds(itemIds),
                BookingState.PAST, () -> bookingRepository.findPastByItemIds(itemIds),
                BookingState.FUTURE, () -> bookingRepository.findFutureByItemIds(itemIds),
                BookingState.WAITING, () -> bookingRepository.findByItemIdsAndStatus(itemIds, BookingStatus.WAITING),
                BookingState.REJECTED, () -> bookingRepository.findByItemIdsAndStatus(itemIds, BookingStatus.REJECTED)
        );

        Supplier<List<Booking>> supplier = stateToMethodMap.get(state);
        if (supplier == null) {
            throw new UnknownStateException("Unknown state: " + state);
        }

        return BookingMapper.convertBookings(supplier.get());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Невозможно найти пользователя с ID: " + userId));
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти вещь с ID: " + itemId));
    }

    private Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти бронирование с ID: " + bookingId));
    }

    private void isTheTimeCorrect(BookingInputDto bookingInputDto) {
        if (bookingInputDto.getEnd().isBefore(bookingInputDto.getStart())) {
            throw new InvalidRequestException("Конец бронирования не может быть раньше начала");
        }
    }
}