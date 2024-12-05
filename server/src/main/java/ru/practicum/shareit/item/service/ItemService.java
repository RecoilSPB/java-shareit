package ru.practicum.shareit.item.service;

import ru.practicum.dto.item.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItemsByUser(Long userId);

    List<ItemDto> getItemByText(String text);

    ItemDto getItemById(Long itemId, Long userId);

    ItemDto createItem(Long userId, ItemDto itemDto);

    ItemDto updateItem(Long userId, ItemDto itemDto, Long itemId);
}