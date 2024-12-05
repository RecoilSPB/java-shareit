package ru.practicum.shareit.item.storage;


import org.springframework.data.domain.Pageable;
import ru.practicum.model.item.Item;

import java.util.List;

public interface ItemStorage {
    List<Item> getItemsByUser(Long userId, Pageable pageable);

    List<Item> getItemByText(String text, Pageable pageable);

    Item getItemById(Long itemId);

    Item addItem(Item item);

    Item updateItem(Item item);
}