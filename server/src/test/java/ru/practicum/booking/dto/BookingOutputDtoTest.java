package ru.practicum.booking.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.booking.model.BookingStatus;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingOutputDtoTest {

    @Test
    void shouldCreateBookingOutputDtoWithValidData() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        ItemDto item = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .build();

        UserDto booker = UserDto.builder()
                .id(2L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        BookingOutputDto dto = BookingOutputDto.builder()
                .id(3L)
                .start(start)
                .end(end)
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build();

        assertEquals(3L, dto.getId());
        assertEquals(start, dto.getStart());
        assertEquals(end, dto.getEnd());
        assertEquals(item, dto.getItem());
        assertEquals(booker, dto.getBooker());
        assertEquals(BookingStatus.APPROVED, dto.getStatus());
    }

    @Test
    void shouldHandleNullFields() {
        BookingOutputDto dto = BookingOutputDto.builder()
                .id(null)
                .start(null)
                .end(null)
                .item(null)
                .booker(null)
                .status(null)
                .build();

        assertNull(dto.getId());
        assertNull(dto.getStart());
        assertNull(dto.getEnd());
        assertNull(dto.getItem());
        assertNull(dto.getBooker());
        assertNull(dto.getStatus());
    }

    @Test
    void shouldSetFieldsCorrectly() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        ItemDto item = ItemDto.builder()
                .id(1L)
                .name("Drill")
                .build();

        UserDto booker = UserDto.builder()
                .id(2L)
                .name("John Doe")
                .build();

        BookingOutputDto dto = new BookingOutputDto();
        dto.setId(4L);
        dto.setStart(start);
        dto.setEnd(end);
        dto.setItem(item);
        dto.setBooker(booker);
        dto.setStatus(BookingStatus.REJECTED);

        assertEquals(4L, dto.getId());
        assertEquals(start, dto.getStart());
        assertEquals(end, dto.getEnd());
        assertEquals(item, dto.getItem());
        assertEquals(booker, dto.getBooker());
        assertEquals(BookingStatus.REJECTED, dto.getStatus());
    }

    @Test
    void shouldTestToString() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        BookingOutputDto dto = BookingOutputDto.builder()
                .id(5L)
                .start(start)
                .end(end)
                .item(null)
                .booker(null)
                .status(BookingStatus.APPROVED)
                .build();

        String expectedString = "BookingOutputDto(id=5, start=" + start + ", end=" + end +
                ", item=null, booker=null, status=APPROVED)";
        assertEquals(expectedString, dto.toString());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        BookingOutputDto dto1 = BookingOutputDto.builder()
                .id(6L)
                .start(start)
                .end(end)
                .item(null)
                .booker(null)
                .status(BookingStatus.WAITING)
                .build();

        BookingOutputDto dto2 = BookingOutputDto.builder()
                .id(6L)
                .start(start)
                .end(end)
                .item(null)
                .booker(null)
                .status(BookingStatus.WAITING)
                .build();

        BookingOutputDto dto3 = BookingOutputDto.builder()
                .id(7L)
                .start(start)
                .end(end)
                .item(null)
                .booker(null)
                .status(BookingStatus.REJECTED)
                .build();

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
