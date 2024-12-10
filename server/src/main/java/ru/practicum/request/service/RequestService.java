package ru.practicum.request.service;

import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto addRequest(Long userId, RequestDto requestDto);

    RequestDto getRequestById(Long requestId, Long userId);

    List<RequestDto> getAllRequests(Long userId, Integer from, Integer size);

    List<RequestDto> getRequestsByUser(Long userId);
}