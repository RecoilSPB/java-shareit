package ru.practicum.booking.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.booking.dto.BookingOutputDto;
import ru.practicum.booking.dto.DateBookingDto;
import ru.practicum.booking.model.Booking;
import ru.practicum.booking.model.BookingStatus;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookingMapperTest {

    @Test
    void toBookingDto() {
        // Arrange
        Booking booking = mock(Booking.class);
        User user = mock(User.class);
        Item item = mock(Item.class);
        User itemOwner = mock(User.class);

        // Настраиваем Booking
        when(booking.getId()).thenReturn(1L);
        when(booking.getStart()).thenReturn(LocalDateTime.of(2024, 1, 1, 10, 0));
        when(booking.getEnd()).thenReturn(LocalDateTime.of(2024, 1, 2, 10, 0));
        when(booking.getStatus()).thenReturn(BookingStatus.APPROVED);
        when(booking.getBooker()).thenReturn(user);  // Связываем с User
        when(booking.getItem()).thenReturn(item);    // Связываем с Item

        // Настраиваем User
        when(user.getId()).thenReturn(101L);
        when(user.getName()).thenReturn("Booker User");

        // Настраиваем Item
        when(item.getId()).thenReturn(201L);
        when(item.getName()).thenReturn("Item Name");
        when(item.getOwner()).thenReturn(itemOwner); // Владелец Item

        // Настраиваем Owner для Item
        when(itemOwner.getId()).thenReturn(102L);
        when(itemOwner.getName()).thenReturn("Owner User");

        // Act
        BookingOutputDto result = BookingMapper.toBookingDto(booking);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0), result.getStart());
        assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), result.getEnd());
        assertEquals(BookingStatus.APPROVED, result.getStatus());

        // Проверяем Booker
        assertNotNull(result.getBooker());
        assertEquals(101L, result.getBooker().getId());
        assertEquals("Booker User", result.getBooker().getName());

        // Проверяем Item
        assertNotNull(result.getItem());
        assertEquals(201L, result.getItem().getId());
        assertEquals("Item Name", result.getItem().getName());
        assertNotNull(result.getItem().getOwner());
        assertEquals(102L, result.getItem().getOwner().getId());
        assertEquals("Owner User", result.getItem().getOwner().getName());
    }



    @Test
    void toDateBookingDto() {
        // Arrange
        Booking booking = mock(Booking.class);

        when(booking.getId()).thenReturn(2L);
        when(booking.getStart()).thenReturn(LocalDateTime.of(2024, 2, 1, 12, 0));
        when(booking.getEnd()).thenReturn(LocalDateTime.of(2024, 2, 2, 12, 0));
        when(booking.getBooker()).thenReturn(mock(User.class));
        when(booking.getBooker().getId()).thenReturn(100L);

        // Act
        DateBookingDto result = BookingMapper.toDateBookingDto(booking);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(100L, result.getBookerId());
        assertEquals(LocalDateTime.of(2024, 2, 1, 12, 0), result.getStart());
        assertEquals(LocalDateTime.of(2024, 2, 2, 12, 0), result.getEnd());
    }

    @Test
    void convertBookings() {
        // Arrange
        Booking booking1 = mock(Booking.class);
        Booking booking2 = mock(Booking.class);

        User user1 = mock(User.class);
        User user2 = mock(User.class);

        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        // Настраиваем Booking 1
        when(booking1.getId()).thenReturn(1L);
        when(booking1.getStart()).thenReturn(LocalDateTime.of(2024, 1, 1, 10, 0));
        when(booking1.getBooker()).thenReturn(user1); // Связь с User
        when(booking1.getItem()).thenReturn(item1);   // Связь с Item

        // Настраиваем Booking 2
        when(booking2.getId()).thenReturn(2L);
        when(booking2.getStart()).thenReturn(LocalDateTime.of(2024, 1, 3, 10, 0));
        when(booking2.getBooker()).thenReturn(user2); // Связь с User
        when(booking2.getItem()).thenReturn(item2);   // Связь с Item

        // Настраиваем User для Booking 1
        when(user1.getId()).thenReturn(101L);
        when(user1.getName()).thenReturn("User 1");

        // Настраиваем User для Booking 2
        when(user2.getId()).thenReturn(102L);
        when(user2.getName()).thenReturn("User 2");

        // Настраиваем Item для Booking 1
        when(item1.getId()).thenReturn(201L);
        when(item1.getName()).thenReturn("Item 1");
        when(item1.getOwner()).thenReturn(user1); // Связь с владельцем

        // Настраиваем Item для Booking 2
        when(item2.getId()).thenReturn(202L);
        when(item2.getName()).thenReturn("Item 2");
        when(item2.getOwner()).thenReturn(user2); // Связь с владельцем

        List<Booking> bookings = List.of(booking1, booking2);

        // Act
        List<BookingOutputDto> result = BookingMapper.convertBookings(bookings);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getId()); // Проверка на сортировку
        assertEquals(1L, result.get(1).getId());

        // Проверка Bookers
        assertEquals(102L, result.get(0).getBooker().getId());
        assertEquals("User 2", result.get(0).getBooker().getName());

        assertEquals(101L, result.get(1).getBooker().getId());
        assertEquals("User 1", result.get(1).getBooker().getName());

        // Проверка Items
        assertEquals(202L, result.get(0).getItem().getId());
        assertEquals("Item 2", result.get(0).getItem().getName());

        assertEquals(201L, result.get(1).getItem().getId());
        assertEquals("Item 1", result.get(1).getItem().getName());
    }

}
