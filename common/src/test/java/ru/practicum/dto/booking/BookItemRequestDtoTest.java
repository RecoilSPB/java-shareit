package ru.practicum.dto.booking;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookItemRequestDtoTest {

    private final Validator validator;

    public BookItemRequestDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenStartDateIsInPast() {
        BookItemRequestDto dto = new BookItemRequestDto(1L, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWithValidDates() {
        BookItemRequestDto dto = new BookItemRequestDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
