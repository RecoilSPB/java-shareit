package ru.practicum.item.comment.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.practicum.validation.CreateObject;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentDtoTest {

    private final Validator validator;

    public CommentDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenTextIsBlank() {
        CommentDto dto = CommentDto.builder()
                .text("")
                .build();

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Текст не должен быть пустым")));
    }

    @Test
    void shouldPassValidationWithValidData() {
        CommentDto dto = CommentDto.builder()
                .text("Valid text")
                .build();

        Set<ConstraintViolation<CommentDto>> violations = validator.validate(dto, CreateObject.class);
        assertTrue(violations.isEmpty());
    }
}
