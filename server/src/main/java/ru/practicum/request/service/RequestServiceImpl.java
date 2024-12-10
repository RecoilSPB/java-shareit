package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.item.dto.ItemForRequestDto;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.item.storage.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;
import ru.practicum.item.paging.CustomPageRequest;
import ru.practicum.request.storage.RequestRepository;
import ru.practicum.user.storage.UserRepository;
import ru.practicum.exception.*;
import ru.practicum.item.mapper.ItemMapper;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.request.mapper.RequestMapper.toRequest;
import static ru.practicum.request.mapper.RequestMapper.toRequestDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public RequestDto addRequest(Long userId, RequestDto requestDto) {
        requestDto.setCreated(LocalDateTime.now());
        return toRequestDto(requestRepository.save(toRequest(requestDto, getUser(userId))));
    }

    @Override
    public RequestDto getRequestById(Long requestId, Long userId) {
        getUser(userId);
        Request request = getRequest(requestId);
        return RequestMapper.toRequestDto(request, getAnswers(request));
    }

    @Override
    public List<RequestDto> getAllRequests(Long userId, Integer from, Integer size) {
        return requestRepository.findByRequestorNot(getUser(userId),
                        CustomPageRequest.create(from, size, Sort.by("created").descending())).stream()
                .map(request -> RequestMapper.toRequestDto(request, getAnswers(request)))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getRequestsByUser(Long userId) {
        return requestRepository.findByRequestor(getUser(userId)).stream()
                .map(request -> RequestMapper.toRequestDto(request, getAnswers(request)))
                .sorted(Comparator.comparing(RequestDto::getCreated, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти. Пользователь отсутствует!"));
    }

    private Request getRequest(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundObjectException("Невозможно найти. Запрос отсутствует!"));
    }

    private List<ItemForRequestDto> getAnswers(Request request) {
        List<Item> itemList = itemRepository.findByRequest(request);
        return itemList.stream()
                .map(ItemMapper::toItemForRequestDto)
                .collect(Collectors.toList());
    }
}