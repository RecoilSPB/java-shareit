package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.item.dto.ItemForRequestDto;
import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.item.storage.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.model.User;
import ru.practicum.item.paging.CustomPageRequest;
import ru.practicum.request.storage.ItemRequestRepository;
import ru.practicum.user.storage.UserRepository;
import ru.practicum.exception.*;
import ru.practicum.item.mapper.ItemMapper;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.request.mapper.ItemRequestMapper.toItemRequest;
import static ru.practicum.request.mapper.ItemRequestMapper.toItemRequestDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto addRequest(Long userId, ItemRequestDto requestDto) {
        requestDto.setCreated(LocalDateTime.now());
        return toItemRequestDto(requestRepository.save(toItemRequest(requestDto, getUser(userId))));
    }

    @Override
    public ItemRequestDto getRequestById(Long requestId, Long userId) {
        getUser(userId);
        ItemRequest request = getRequest(requestId);
        return toItemRequestDto(request, getAnswers(request));
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long userId, Integer from, Integer size) {
        return requestRepository.findByRequestorNot(getUser(userId),
                        CustomPageRequest.create(from, size, Sort.by("created").descending())).stream()
                .map(request -> toItemRequestDto(request, getAnswers(request)))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getRequestsByUser(Long userId) {
        return requestRepository.findByRequestor(getUser(userId)).stream()
                .map(request -> toItemRequestDto(request, getAnswers(request)))
                .sorted(Comparator.comparing(ItemRequestDto::getCreated, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти. Пользователь отсутствует!"));
    }

    private ItemRequest getRequest(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти. Запрос отсутствует!"));
    }

    private List<ItemForRequestDto> getAnswers(ItemRequest itemRequest) {
        List<Item> itemList = itemRepository.findByRequest(itemRequest);
        return itemList.stream()
                .map(ItemMapper::toItemForRequestDto)
                .collect(Collectors.toList());
    }
}