package ru.practicum.booking.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingInputDtoTest {

    private final Validator validator;

    public BookingInputDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldCreateBookingInputDtoWithValidData() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        BookingInputDto dto = BookingInputDto.builder()
                .id(1L)
                .start(start)
                .end(end)
                .itemId(100L)
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(start, dto.getStart());
        assertEquals(end, dto.getEnd());
        assertEquals(100L, dto.getItemId());
    }

    @Test
    void shouldHandleNullFields() {
        BookingInputDto dto = BookingInputDto.builder()
                .id(null)
                .start(null)
                .end(null)
                .itemId(null)
                .build();

        assertNull(dto.getId());
        assertNull(dto.getStart());
        assertNull(dto.getEnd());
        assertNull(dto.getItemId());
    }

    @Test
    void shouldSetFieldsCorrectly() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        BookingInputDto dto = new BookingInputDto();
        dto.setId(2L);
        dto.setStart(start);
        dto.setEnd(end);
        dto.setItemId(101L);

        assertEquals(2L, dto.getId());
        assertEquals(start, dto.getStart());
        assertEquals(end, dto.getEnd());
        assertEquals(101L, dto.getItemId());
    }

    @Test
    void shouldTestToString() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        BookingInputDto dto = BookingInputDto.builder()
                .id(3L)
                .start(start)
                .end(end)
                .itemId(102L)
                .build();

        String expectedString = "BookingInputDto(id=3, start=" + start + ", end=" + end + ", itemId=102)";
        assertEquals(expectedString, dto.toString());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 15, 12, 0);

        BookingInputDto dto1 = BookingInputDto.builder()
                .id(1L)
                .start(start)
                .end(end)
                .itemId(100L)
                .build();

        BookingInputDto dto2 = BookingInputDto.builder()
                .id(1L)
                .start(start)
                .end(end)
                .itemId(100L)
                .build();

        BookingInputDto dto3 = BookingInputDto.builder()
                .id(2L)
                .start(start)
                .end(end)
                .itemId(101L)
                .build();

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
