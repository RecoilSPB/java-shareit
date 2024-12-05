package ru.practicum.mapper.item;

import ru.practicum.dto.booking.DateBookingDto;
import ru.practicum.dto.item.ItemForRequestDto;
import ru.practicum.dto.item.comment.CommentDto;
import ru.practicum.dto.item.ItemDto;
import ru.practicum.model.item.Item;
import ru.practicum.model.request.ItemRequest;

import java.util.List;

import static ru.practicum.mapper.user.UserMapper.toUser;
import static ru.practicum.mapper.user.UserMapper.toUserDto;

public class ItemMapper {
    public static ItemDto toItemDto(
            Item item,
            List<CommentDto> comments,
            DateBookingDto lastBooking,
            DateBookingDto nextBooking
    ) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(toUserDto(item.getOwner()))
                .comments(comments)
                .lastBooking(lastBooking)
                .nextBooking(nextBooking)
                .build();
    }

    public static ItemDto toItemDto(Item item) {
        ItemRequest request = item.getRequest();
        Long requestId = request != null ? request.getId() : null;
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(toUserDto(item.getOwner()))
                .requestId(requestId)
                .build();
    }

    public static ItemDto toItemDto(Item item, List<CommentDto> comments) {
        ItemRequest request = item.getRequest();
        Long requestId = request != null ? request.getId() : null;
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(toUserDto(item.getOwner()))
                .requestId(requestId)
                .comments(comments)
                .build();
    }

    public static Item toItem(ItemDto itemDto, ItemRequest request) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(toUser(itemDto.getOwner()))
                .request(request)
                .build();
    }

    public static ItemForRequestDto toItemForRequestDto(Item item) {
        return ItemForRequestDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequest().getId())
                .build();
    }
}