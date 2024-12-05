package ru.practicum.shareit.item.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.model.item.Item;
import ru.practicum.model.user.User;
import ru.practicum.exception.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Repository
public class ItemStorageImpl implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private long id = 1;

    @Override
    public List<Item> getItemsByUser(Long userId, Pageable pageable) {
        log.info("Получен запрос на вывод вещей определенного пользователя");
        return items.values().stream()
                .filter(item -> Objects.equals(item.getOwner().getId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getItemByText(String text, Pageable pageable) {
        log.info("Получен запрос на поиск вещи по названию или описанию");
        return items.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> mergeNameAndDesc(item.getId()).toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Item getItemById(Long itemId) {
        Item item = checkAvailability("найти", itemId);
        log.info("Получен запрос на получение вещи по id");
        return item;

    }

    @Override
    public Item addItem(Item item) {
        item.setId(getId());
        items.put(item.getId(), item);
        log.info("Получен запрос на добавление вещи");
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        log.info("Получен запрос на изменение характеристик вещи с id {}", item.getId());
        Item existingItem = checkAvailability("изменить", item.getId());
        checkOwner(existingItem, item.getOwner());

        if (item.getName() != null && !item.getName().isBlank()) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }

        log.info("Характеристики вещи с id {} успешно изменены", item.getId());
        return existingItem;
    }


    private long getId() {
        return id++;
    }

    private Item checkAvailability(String operation, long id) {
        if (!items.containsKey(id)) {
            log.warn("Вещь с id {} не найдена!", id);
            throw new NotFoundObjectException("Невозможно " + operation + ". Вещь не найдена!");
        }
        return items.get(id);
    }

    private void checkOwner(Item item, User user) {
        if (item.getOwner() != user) {
            throw new NotFoundObjectException("Вещь принадлежит другому пользователю!");
        }
    }

    private String mergeNameAndDesc(Long itemId) {
        Item item = items.get(itemId);
        String merge = item.getName() + " " + item.getDescription();
        return merge.toLowerCase();
    }
}