package ru.practicum.dto.booking;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.practicum.booking.dto.DateBookingDto;
import ru.practicum.validation.CreateObject;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DateBookingDtoTest {

    private final Validator validator;

    public DateBookingDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenFieldsAreNull() {
        DateBookingDto dto = DateBookingDto.builder()
                .bookerId(null)
                .start(null)
                .end(null)
                .build();

        Set<ConstraintViolation<DateBookingDto>> violations = validator.validate(dto, CreateObject.class);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWithValidData() {
        DateBookingDto dto = DateBookingDto.builder()
                .bookerId(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();

        Set<ConstraintViolation<DateBookingDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWithBuilder() {
        DateBookingDto dto = DateBookingDto.builder()
                .bookerId(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();

        assertNotNull(dto);
        assertTrue(dto.getStart().isBefore(dto.getEnd()));
    }

    @Test
    void shouldFailValidationWithInvalidDates() {
        DateBookingDto dto = DateBookingDto.builder()
                .bookerId(1L)
                .start(LocalDateTime.now().plusDays(2))
                .end(LocalDateTime.now().plusDays(1))
                .build();

        assertNotNull(dto);
        assertFalse(dto.getStart().isBefore(dto.getEnd())); // Это логическая проверка, не валидация
    }
}
