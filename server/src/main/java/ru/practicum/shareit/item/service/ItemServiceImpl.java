package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.item.ItemDto;
import ru.practicum.model.item.Item;
import ru.practicum.mapper.item.ItemMapper;
import ru.practicum.paging.item.CustomPageRequest;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.user.UserMapper.toUserDto;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    @Autowired
    public ItemServiceImpl(@Qualifier("userStorageImpl") UserStorage userStorage,
                           @Qualifier("itemStorageImpl") ItemStorage itemStorage) {
        this.userStorage = userStorage;
        this.itemStorage = itemStorage;
    }

    @Override
    public List<ItemDto> getItemsByUser(Long userId, Integer from, Integer size) {
        log.info("Получен запрос на список вещей по хозяину");
        Pageable pageable = CustomPageRequest.create(from, size, Sort.by(Sort.Direction.ASC, "id"));
        userStorage.checkUserAvailability("найти", userId);
        return itemStorage.getItemsByUser(userId, pageable).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getItemByText(String text, Integer from, Integer size) {
        log.info("Получен запрос на поиск вещи по названию или описанию");
        Pageable pageable = CustomPageRequest.create(from, size, Sort.by(Sort.Direction.ASC, "id"));
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getItemByText(text, pageable).stream()
                .filter(Item::getAvailable)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItemById(Long itemId, Long userId) {
        userStorage.checkUserAvailability("найти", userId);
        Item item = itemStorage.getItemById(itemId);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) {
        userStorage.checkUserAvailability("найти", userId);
        itemDto.setOwner(toUserDto(userStorage.getUserById(userId)));
        Item item = itemStorage.addItem(ItemMapper.toItem(itemDto, null));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, ItemDto itemDto, Long itemId) {
        userStorage.checkUserAvailability("найти", userId);
        itemDto.setOwner(toUserDto(userStorage.getUserById(userId)));
        itemDto.setId(itemId);
        Item item = itemStorage.updateItem(ItemMapper.toItem(itemDto, null));
        return ItemMapper.toItemDto(item);
    }
}