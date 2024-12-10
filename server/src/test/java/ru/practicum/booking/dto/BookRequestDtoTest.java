package ru.practicum.booking.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BookRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationForValidData() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        BookRequestDto dto = new BookRequestDto(1L, start, end);

        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldAllowNullDates() {
        BookRequestDto dto = new BookRequestDto(1L, null, null);

        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
