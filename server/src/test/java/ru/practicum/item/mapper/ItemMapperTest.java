package ru.practicum.item.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.booking.dto.DateBookingDto;
import ru.practicum.item.comment.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.Request;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ItemMapperTest {

    @Test
    void toItemDto_withAllFields() {
        // Arrange
        Item item = Mockito.mock(Item.class);
        User user = Mockito.mock(User.class);
        List<CommentDto> comments = List.of(Mockito.mock(CommentDto.class));
        DateBookingDto lastBooking = Mockito.mock(DateBookingDto.class);
        DateBookingDto nextBooking = Mockito.mock(DateBookingDto.class);

        when(item.getId()).thenReturn(1L);
        when(item.getName()).thenReturn("Test Item");
        when(item.getDescription()).thenReturn("Test Description");
        when(item.getAvailable()).thenReturn(true);
        when(item.getOwner()).thenReturn(user);
        when(user.getId()).thenReturn(101L);
        when(user.getName()).thenReturn("Test User");

        // Act
        ItemDto result = ItemMapper.toItemDto(item, comments, lastBooking, nextBooking);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertTrue(result.getAvailable());
        assertEquals(comments, result.getComments());
        assertEquals(lastBooking, result.getLastBooking());
        assertEquals(nextBooking, result.getNextBooking());
        assertEquals(101L, result.getOwner().getId());
        assertEquals("Test User", result.getOwner().getName());
    }

    @Test
    void toItemDto_withoutRequest() {
        // Arrange
        Item item = Mockito.mock(Item.class);
        User user = Mockito.mock(User.class);

        when(item.getId()).thenReturn(1L);
        when(item.getName()).thenReturn("Test Item");
        when(item.getDescription()).thenReturn("Test Description");
        when(item.getAvailable()).thenReturn(true);
        when(item.getRequest()).thenReturn(null);
        when(item.getOwner()).thenReturn(user); // Замокали владельца
        when(user.getId()).thenReturn(101L); // Указываем id для мока
        when(user.getName()).thenReturn("Test User");

        // Act
        ItemDto result = ItemMapper.toItemDto(item);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertTrue(result.getAvailable());
        assertNull(result.getRequestId());
        assertEquals(101L, result.getOwner().getId()); // Проверяем владельца
        assertEquals("Test User", result.getOwner().getName());
    }


    @Test
    void toItem() {
        // Arrange
        ItemDto itemDto = Mockito.mock(ItemDto.class);
        UserDto userDto = Mockito.mock(UserDto.class);
        Request request = Mockito.mock(Request.class);

        when(itemDto.getId()).thenReturn(1L);
        when(itemDto.getName()).thenReturn("Test Item");
        when(itemDto.getDescription()).thenReturn("Test Description");
        when(itemDto.getAvailable()).thenReturn(true);
        when(itemDto.getOwner()).thenReturn(userDto);
        when(userDto.getId()).thenReturn(102L);
        when(userDto.getName()).thenReturn("Owner Name");

        // Act
        Item result = ItemMapper.toItem(itemDto, request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertTrue(result.getAvailable());
        assertEquals(102L, result.getOwner().getId());
        assertEquals("Owner Name", result.getOwner().getName());
    }

    @Test
    void toItemDto_withCommentsAndRequest() {
        // Arrange
        Item item = Mockito.mock(Item.class);
        Request request = Mockito.mock(Request.class);
        List<CommentDto> comments = List.of(Mockito.mock(CommentDto.class));

        when(item.getId()).thenReturn(1L);
        when(item.getName()).thenReturn("Commented Item");
        when(item.getDescription()).thenReturn("Item with comments");
        when(item.getAvailable()).thenReturn(true);
        when(item.getRequest()).thenReturn(request);
        when(request.getId()).thenReturn(2001L);
        when(item.getOwner()).thenReturn(Mockito.mock(User.class));

        // Act
        ItemDto result = ItemMapper.toItemDto(item, comments);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Commented Item", result.getName());
        assertEquals("Item with comments", result.getDescription());
        assertTrue(result.getAvailable());
        assertEquals(2001L, result.getRequestId());
        assertEquals(comments, result.getComments());
    }

    @Test
    void toItem_withNullRequest() {
        // Arrange
        ItemDto itemDto = Mockito.mock(ItemDto.class);
        UserDto userDto = Mockito.mock(UserDto.class);

        when(itemDto.getId()).thenReturn(1L);
        when(itemDto.getName()).thenReturn("No Request Item");
        when(itemDto.getDescription()).thenReturn("Item without request");
        when(itemDto.getAvailable()).thenReturn(true);
        when(itemDto.getOwner()).thenReturn(userDto);
        when(userDto.getId()).thenReturn(103L);

        // Act
        Item result = ItemMapper.toItem(itemDto, null);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("No Request Item", result.getName());
        assertEquals("Item without request", result.getDescription());
        assertTrue(result.getAvailable());
        assertEquals(103L, result.getOwner().getId());
        assertNull(result.getRequest());
    }

}
