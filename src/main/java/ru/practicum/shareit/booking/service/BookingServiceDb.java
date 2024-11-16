package ru.practicum.shareit.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.comparator.BookingComparator;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.state.BookingState;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceDb implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public BookingServiceDb(BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
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
    public List<BookingOutputDto> getAllBookings(Long bookerId, BookingState state) {
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

        return convertBookings(supplier.get());
    }


    @Override
    public List<BookingOutputDto> getAllBookingsForOwner(Long ownerId, BookingState state) {
        User owner = getUser(ownerId);
        List<Item> items = itemRepository.findByOwner(owner);
        log.info("Получен запрос на получение списка бронирований по владельцу");
        return items.stream()
                .flatMap((item) -> getAllBookingsForItem(item, state).stream())
                .collect(Collectors.toList());
    }

    private List<BookingOutputDto> getAllBookingsForItem(Item item, BookingState state) {
        Map<BookingState, Supplier<List<Booking>>> stateToMethodMap = Map.of(
                BookingState.ALL, () -> bookingRepository.findByItem(item),
                BookingState.CURRENT, () -> bookingRepository.findCurrentByItem(item),
                BookingState.PAST, () -> bookingRepository.findByItemAndEndIsBefore(item, LocalDateTime.now()),
                BookingState.FUTURE, () -> bookingRepository.findByItemAndStartIsAfter(item, LocalDateTime.now()),
                BookingState.WAITING, () -> bookingRepository.findByItemAndStatus(item, BookingStatus.WAITING),
                BookingState.REJECTED, () -> bookingRepository.findByItemAndStatus(item, BookingStatus.REJECTED)
        );

        Supplier<List<Booking>> supplier = stateToMethodMap.get(state);
        if (supplier == null) {
            throw new UnknownStateException("Unknown state: " + state);
        }

        return convertBookings(supplier.get());
    }


    private List<BookingOutputDto> convertBookings(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .sorted(new BookingComparator().reversed())
                .collect(Collectors.toList());
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