package ru.practicum.request.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestDtoTest {

    private final Validator validator;

    public RequestDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenDescriptionIsBlank() {
        RequestDto dto = RequestDto.builder()
                .description("")
                .build();

        Set<ConstraintViolation<RequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Отсутствует описание обьекта, которое вы ищите")));
    }

    @Test
    void shouldPassValidationWithValidData() {
        RequestDto dto = RequestDto.builder()
                .description("Valid description")
                .build();

        Set<ConstraintViolation<RequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

}
