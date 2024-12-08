package ru.practicum.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemRequestDtoTest {

    private final Validator validator;

    public ItemRequestDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenDescriptionIsBlank() {
        ItemRequestDto dto = ItemRequestDto.builder()
                .description("")
                .build();

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Отсутствует описание обьекта, которое вы ищите")));
    }

    @Test
    void shouldPassValidationWithValidData() {
        ItemRequestDto dto = ItemRequestDto.builder()
                .description("Valid description")
                .build();

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

}
