package ru.practicum.shareit.item.service;

import ru.practicum.dto.item.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItemsByUser(Long userId, Integer from, Integer size);

    List<ItemDto> getItemByText(String text, Integer from, Integer size);

    ItemDto getItemById(Long itemId, Long userId);

    ItemDto createItem(Long userId, ItemDto itemDto);

    ItemDto updateItem(Long userId, ItemDto itemDto, Long itemId);
}