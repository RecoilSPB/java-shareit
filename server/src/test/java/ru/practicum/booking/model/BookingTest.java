package ru.practicum.booking.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

public class BookingTest {

    @Test
    public void bookingEqualityTest() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 23, 58, 00);
        LocalDateTime end = LocalDateTime.of(2025, 1, 1, 23, 59, 00);

        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Booking b1 = new Booking();
        b1.setId(1L);
        b1.setStart(start);
        b1.setEnd(end);
        b1.setItem(i1);
        b1.setBooker(u1);
        b1.setStatus(BookingStatus.WAITING);

        Booking b2 = new Booking();
        b2.setId(1L);
        b2.setStart(start);
        b2.setEnd(end);
        b2.setItem(i1);
        b2.setBooker(u1);
        b2.setStatus(BookingStatus.WAITING);

        Assertions.assertEquals(b1.hashCode(), b2.hashCode());
        Assertions.assertEquals(b1, b2);
    }

    @Test
    public void bookingInequalityTest() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 23, 58, 00);
        LocalDateTime end = LocalDateTime.of(2025, 1, 1, 23, 59, 00);

        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Booking b1 = new Booking();
        b1.setId(1L);
        b1.setStart(start);
        b1.setEnd(end);
        b1.setItem(i1);
        b1.setBooker(u1);
        b1.setStatus(BookingStatus.WAITING);

        Booking b2 = new Booking();
        b2.setId(2L);
        b2.setStart(start);
        b2.setEnd(end);
        b2.setItem(i1);
        b2.setBooker(u1);
        b2.setStatus(BookingStatus.WAITING);
        Assertions.assertNotEquals(b1, b2);
    }

    @Test
    public void bookingInequalityTestWithNull() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 23, 58, 00);
        LocalDateTime end = LocalDateTime.of(2025, 1, 1, 23, 59, 00);

        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Booking b1 = new Booking();
        b1.setId(null);
        b1.setStart(start);
        b1.setEnd(end);
        b1.setItem(i1);
        b1.setBooker(u1);
        b1.setStatus(BookingStatus.WAITING);

        Booking b2 = new Booking();
        b2.setId(2L);
        b2.setStart(start);
        b2.setEnd(end);
        b2.setItem(i1);
        b2.setBooker(u1);
        b2.setStatus(BookingStatus.WAITING);

        Assertions.assertNotEquals(b1, b2);
    }

}