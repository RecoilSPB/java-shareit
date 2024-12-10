package ru.practicum.item.service;

import ru.practicum.item.comment.dto.CommentDto;

public interface CommentService {
    CommentDto addComment(Long itemId, Long authorId, CommentDto commentDto);
}