package ru.practicum.dto.booking;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.practicum.dto.item.ItemDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.booking.BookingStatus;
import ru.practicum.validation.CreateObject;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingOutputDtoTest {

    private final Validator validator;

    public BookingOutputDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenFieldsAreNull() {
        BookingOutputDto dto = BookingOutputDto.builder()
                .item(null)
                .booker(null)
                .status(null)
                .build();

        Set<ConstraintViolation<BookingOutputDto>> violations = validator.validate(dto, CreateObject.class);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWithValidData() {
        BookingOutputDto dto = BookingOutputDto.builder()
                .item(ItemDto.builder().id(1L).name("Test Item").available(true).build())
                .booker(UserDto.builder().id(1L).name("Test User").email("test@example.com").build())
                .status(BookingStatus.APPROVED)
                .build();

        Set<ConstraintViolation<BookingOutputDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
