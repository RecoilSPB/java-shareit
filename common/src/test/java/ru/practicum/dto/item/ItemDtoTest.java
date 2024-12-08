package ru.practicum.dto.item;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.validation.CreateObject;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDtoTest {

    private final Validator validator;

    public ItemDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenNameIsBlank() {
        ItemDto dto = ItemDto.builder()
                .name("")
                .description("Valid description")
                .available(true)
                .build();

        // Указываем группу CreateObject.class
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(dto, CreateObject.class);

        assertEquals(1, violations.size()); // Ожидаем одну ошибку валидации
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Название не может быть пустым")));
    }


    @Test
    void shouldPassValidationWithValidData() {
        ItemDto dto = ItemDto.builder()
                .name("Valid name")
                .description("Valid description")
                .available(true)
                .build();

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
