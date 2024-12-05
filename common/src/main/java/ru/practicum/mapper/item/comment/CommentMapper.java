package ru.practicum.mapper.item.comment;

import ru.practicum.model.item.Item;
import ru.practicum.model.item.comment.Comment;
import ru.practicum.model.user.User;
import ru.practicum.dto.item.comment.CommentDto;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static Comment toComment(CommentDto commentDto, User author, Item item) {
        return Comment.builder()
                .author(author)
                .created(commentDto.getCreated())
                .id(commentDto.getId())
                .text(commentDto.getText())
                .item(item)
                .build();
    }
}