package ru.practicum.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.booking.dto.DateBookingDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.comment.dto.CommentDto;
import ru.practicum.exception.InvalidRequestException;
import ru.practicum.exception.NotFoundObjectException;
import ru.practicum.exception.OtherDataException;
import ru.practicum.item.storage.ItemRepository;
import ru.practicum.booking.mapper.BookingMapper;
import ru.practicum.item.mapper.ItemMapper;
import ru.practicum.item.comment.mapper.CommentMapper;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.model.Item;
import ru.practicum.item.comment.model.Comment;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;
import ru.practicum.item.paging.CustomPageRequest;
import ru.practicum.booking.storage.BookingRepository;
import ru.practicum.item.comment.storage.CommentRepository;
import ru.practicum.request.storage.RequestRepository;
import ru.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.user.mapper.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceDb implements ItemService, CommentService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<ItemDto> getItemsByUser(Long userId, Integer from, Integer size) {
        log.info("Получен запрос на список вещей по хозяину");
        Pageable pageable = CustomPageRequest.create(from, size, Sort.by(Sort.Direction.ASC, "id"));
        return itemRepository.findByOwner(getUser(userId), pageable).stream()
                .map(item -> fillItemDto(item, userId))
                .sorted(Comparator.comparing(ItemDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getItemByText(String text, Integer from, Integer size) {
        log.info("Получен запрос на поиск вещи по названию или описанию");
        Pageable pageable = CustomPageRequest.create(from, size, Sort.by(Sort.Direction.ASC, "id"));
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.search(text.toLowerCase(), pageable).stream()
                .filter(Item::getAvailable)
                .map(item -> ItemMapper.toItemDto(item, getComments(item.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItemById(Long itemId, Long userId) {
        log.info("Получен запрос на получение вещи");
        Item item = getItem(itemId);
        return ItemMapper.toItemDto(item,
                getComments(itemId),
                getLastBooking(item, userId),
                getNextBooking(item, userId));
    }

    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) {
        log.info("Получен запрос на добавление вещи");
        itemDto.setOwner(toUserDto(getUser(userId)));
        Long requestId = itemDto.getRequestId();
        Request request = requestId != null ? getRequest(requestId) : null;
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(itemDto, request)));
    }

    private Request getRequest(Long requestId) {
        return requestRepository.findById(requestId).orElse(null);
    }

    @Override
    public ItemDto updateItem(Long userId, ItemDto itemDto, Long itemId) {
        log.info("Получен запрос на изменение вещи");
        User user = getUser(userId);
        Item item = getItem(itemId);

        if (!user.equals(itemRepository.getItemOwner(itemId))) {
            throw new OtherDataException("Редактировать может только владелец");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public CommentDto addComment(Long itemId, Long authorId, CommentDto commentDto) {
        log.info("Получен запрос на добавление комментария");
        Item item = getItem(itemId);
        User author = getUser(authorId);
        isBooker(item, author);
        isTheBookingCompleted(item, author);
        commentDto.setCreated(LocalDateTime.now());
        Comment comment = CommentMapper.toComment(commentDto, author, item);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    private void isBooker(Item item, User user) {
        boolean isBooker = bookingRepository.findByBooker(user).stream()
                .anyMatch(booking -> booking.getItem().equals(item));
        if (!isBooker) {
            throw new InvalidRequestException("Невозможно оставить комментарий." +
                    "Пользователь с ID " + user.getId() + " никогда не бронировал вещь с ID " + item.getId());
        }
    }

    private void isTheBookingCompleted(Item item, User user) {
        List<Booking> bookings = bookingRepository.findByBookerAndItem(user, item);
        boolean isEnd = bookings.stream()
                .anyMatch(booking -> booking.getEnd().isBefore(LocalDateTime.now()));
        if (!isEnd) {
            throw new InvalidRequestException("Невозможно оставить комментарий. " +
                    "Бронирование с ID " + bookings.get(0).getId() + " не завершено");
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти пользователя с ID: " + userId));
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти вещь с ID: " + itemId));
    }

    private ItemDto fillItemDto(Item item, Long userId) {
        return ItemMapper.toItemDto(item,
                getComments(item.getId()),
                getLastBooking(item, userId),
                getNextBooking(item, userId));
    }

    private List<CommentDto> getComments(Long itemId) {
        return commentRepository.findByItemId(itemId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    private DateBookingDto getLastBooking(Item item, Long userId) {
        if (!item.getOwner().getId().equals(userId)) {
            return null;
        }
        List<Booking> bookings = bookingRepository.findPastByItemIds(Collections.singletonList(item.getId()));
        if (bookings.isEmpty()) {
            return null;
        }
        return BookingMapper.toDateBookingDto(bookings.get(bookings.size() - 1));
    }

    private DateBookingDto getNextBooking(Item item, Long userId) {
        if (!item.getOwner().getId().equals(userId)) return null;
        List<Booking> bookings = bookingRepository.findFutureByItemIds(Collections.singletonList(item.getId()));
        if (bookings.isEmpty()) {
            return null;
        }
        return BookingMapper.toDateBookingDto(bookings.get(0));
    }
}