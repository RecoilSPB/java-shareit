package ru.practicum.item.comment.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentDtoTest {

    @Test
    void shouldCreateCommentDtoWithValidData() {
        LocalDateTime createdTime = LocalDateTime.now();
        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .text("Great item!")
                .authorName("John Doe")
                .created(createdTime)
                .build();

        assertEquals(1L, commentDto.getId());
        assertEquals("Great item!", commentDto.getText());
        assertEquals("John Doe", commentDto.getAuthorName());
        assertEquals(createdTime, commentDto.getCreated());
    }

    @Test
    void shouldHandleNullFields() {
        CommentDto commentDto = CommentDto.builder()
                .id(null)
                .text(null)
                .authorName(null)
                .created(null)
                .build();

        assertNull(commentDto.getId());
        assertNull(commentDto.getText());
        assertNull(commentDto.getAuthorName());
        assertNull(commentDto.getCreated());
    }

    @Test
    void shouldSetFieldsCorrectly() {
        LocalDateTime createdTime = LocalDateTime.now();
        CommentDto commentDto = new CommentDto();

        commentDto.setId(2L);
        commentDto.setText("Nice product!");
        commentDto.setAuthorName("Jane Doe");
        commentDto.setCreated(createdTime);

        assertEquals(2L, commentDto.getId());
        assertEquals("Nice product!", commentDto.getText());
        assertEquals("Jane Doe", commentDto.getAuthorName());
        assertEquals(createdTime, commentDto.getCreated());
    }

    @Test
    void shouldTestToString() {
        LocalDateTime createdTime = LocalDateTime.now();
        CommentDto commentDto = CommentDto.builder()
                .id(3L)
                .text("Amazing quality!")
                .authorName("Alice")
                .created(createdTime)
                .build();

        String expectedString = "CommentDto(id=3, text=Amazing quality!, authorName=Alice, created=" + createdTime + ")";
        assertEquals(expectedString, commentDto.toString());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        LocalDateTime createdTime = LocalDateTime.now();
        CommentDto comment1 = CommentDto.builder()
                .id(1L)
                .text("Good!")
                .authorName("User A")
                .created(createdTime)
                .build();

        CommentDto comment2 = CommentDto.builder()
                .id(1L)
                .text("Good!")
                .authorName("User A")
                .created(createdTime)
                .build();

        CommentDto comment3 = CommentDto.builder()
                .id(2L)
                .text("Not bad!")
                .authorName("User B")
                .created(createdTime)
                .build();

        assertEquals(comment1, comment2);
        assertNotEquals(comment1, comment3);
        assertEquals(comment1.hashCode(), comment2.hashCode());
        assertNotEquals(comment1.hashCode(), comment3.hashCode());
    }
}
