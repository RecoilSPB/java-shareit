package ru.practicum.item.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.booking.dto.DateBookingDto;
import ru.practicum.item.comment.dto.CommentDto;
import ru.practicum.user.dto.UserDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDtoTest {

    @Test
    void shouldCreateItemDtoWithValidData() {
        UserDto owner = UserDto.builder().id(1L).name("Owner").email("owner@example.com").build();
        CommentDto comment = CommentDto.builder().id(1L).text("Great item!").build();
        DateBookingDto lastBooking = DateBookingDto.builder().id(1L).build();
        DateBookingDto nextBooking = DateBookingDto.builder().id(2L).build();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .owner(owner)
                .comments(List.of(comment))
                .lastBooking(lastBooking)
                .nextBooking(nextBooking)
                .requestId(123L)
                .build();

        assertEquals(1L, itemDto.getId());
        assertEquals("Drill", itemDto.getName());
        assertEquals("A power drill", itemDto.getDescription());
        assertTrue(itemDto.getAvailable());
        assertEquals(owner, itemDto.getOwner());
        assertEquals(1, itemDto.getComments().size());
        assertEquals(comment, itemDto.getComments().get(0));
        assertEquals(lastBooking, itemDto.getLastBooking());
        assertEquals(nextBooking, itemDto.getNextBooking());
        assertEquals(123L, itemDto.getRequestId());
    }

    @Test
    void shouldHandleNullFields() {
        ItemDto itemDto = ItemDto.builder()
                .id(null)
                .name(null)
                .description(null)
                .available(null)
                .owner(null)
                .comments(null)
                .lastBooking(null)
                .nextBooking(null)
                .requestId(null)
                .build();

        assertNull(itemDto.getId());
        assertNull(itemDto.getName());
        assertNull(itemDto.getDescription());
        assertNull(itemDto.getAvailable());
        assertNull(itemDto.getOwner());
        assertNull(itemDto.getComments());
        assertNull(itemDto.getLastBooking());
        assertNull(itemDto.getNextBooking());
        assertNull(itemDto.getRequestId());
    }

    @Test
    void shouldHandleEmptyCommentsList() {
        ItemDto itemDto = ItemDto.builder()
                .comments(Collections.emptyList())
                .build();

        assertNotNull(itemDto.getComments());
        assertTrue(itemDto.getComments().isEmpty());
    }

    @Test
    void shouldTestToString() {
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .build();

        String expectedString = "ItemDto(id=1, name=Drill, description=A power drill, available=true, owner=null, comments=null, lastBooking=null, nextBooking=null, requestId=null)";
        assertEquals(expectedString, itemDto.toString());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        ItemDto item1 = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .build();

        ItemDto item2 = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .build();

        ItemDto item3 = ItemDto.builder()
                .id(2L)
                .name("Hammer")
                .description("A simple hammer")
                .available(false)
                .build();

        assertEquals(item1, item2);
        assertNotEquals(item1, item3);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1.hashCode(), item3.hashCode());
    }
}
