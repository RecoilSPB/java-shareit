package ru.practicum.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.practicum.user.dto.UserDto;
import ru.practicum.validation.CreateObject;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDtoTest {

    private final Validator validator;

    public UserDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenNameIsBlank() {
        UserDto userDto = UserDto.builder()
                .name("")
                .email("valid@example.com")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto, CreateObject.class);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(violation -> violation.getMessage().equals("Имя не может быть пустым")));
    }

    @Test
    void shouldPassValidationWithValidData() {
        UserDto userDto = UserDto.builder()
                .name("Valid Name")
                .email("valid@example.com")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }
}
