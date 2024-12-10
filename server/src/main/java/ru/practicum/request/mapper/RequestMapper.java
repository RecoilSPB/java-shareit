package ru.practicum.request.mapper;

import ru.practicum.item.dto.ItemForRequestDto;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;

public class RequestMapper {
    public static RequestDto toRequestDto(Request request, List<ItemForRequestDto> answers) {
        return RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(answers)
                .build();
    }

    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .build();
    }

    public static Request toRequest(RequestDto requestDto, User user) {
        return Request.builder()
                .id(requestDto.getId())
                .description(requestDto.getDescription())
                .created(requestDto.getCreated())
                .requestor(user)
                .build();
    }

}