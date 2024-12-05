package ru.practicum.shareit.item.service;

import ru.practicum.dto.item.comment.CommentDto;

public interface CommentService {
    CommentDto addComment(Long itemId, Long authorId, CommentDto commentDto);
}