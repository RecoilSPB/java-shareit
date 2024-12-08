package ru.practicum.item.mapper.comment;

import org.junit.jupiter.api.Test;
import ru.practicum.item.comment.dto.CommentDto;
import ru.practicum.item.comment.mapper.CommentMapper;
import ru.practicum.item.model.Item;
import ru.practicum.item.comment.model.Comment;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CommentMapperTest {

    @Test
    void toCommentDto_shouldMapCorrectly() {
        User author = User.builder().id(1L).name("Author").build();
        Comment comment = Comment.builder()
                .id(1L)
                .text("This is a comment")
                .author(author)
                .created(LocalDateTime.now())
                .build();

        CommentDto result = CommentMapper.toCommentDto(comment);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(comment.getId());
        assertThat(result.getText()).isEqualTo(comment.getText());
        assertThat(result.getAuthorName()).isEqualTo(author.getName());
        assertThat(result.getCreated()).isEqualTo(comment.getCreated());
    }

    @Test
    void toComment_shouldMapCorrectly() {
        CommentDto dto = CommentDto.builder()
                .id(2L)
                .text("New Comment")
                .created(LocalDateTime.now())
                .build();

        User author = User.builder().id(1L).name("Test Author").build();
        Item item = Item.builder().id(1L).name("Test Item").build();

        Comment result = CommentMapper.toComment(dto, author, item);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dto.getId());
        assertThat(result.getText()).isEqualTo(dto.getText());
        assertThat(result.getAuthor()).isEqualTo(author);
        assertThat(result.getItem()).isEqualTo(item);
    }
}
