package ru.practicum.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.practicum.item.dto.ItemForRequestDto;
import ru.practicum.item.mapper.ItemMapper;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.ItemRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemForRequestDtoTest {

    private final Validator validator;

    public ItemForRequestDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenDescriptionIsBlank() {
        ItemForRequestDto dto = ItemForRequestDto.builder()
                .description("")
                .build();

        Set<ConstraintViolation<ItemForRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Обьект без описания")));
    }

    @Test
    void shouldPassValidationWithValidData() {
        ItemForRequestDto dto = ItemForRequestDto.builder()
                .description("Valid description")
                .build();

        Set<ConstraintViolation<ItemForRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void toItemForRequestDto() {
        // Arrange
        Item item = mock(Item.class);
        ItemRequest request = mock(ItemRequest.class);

        when(item.getId()).thenReturn(1L);
        when(item.getName()).thenReturn("Request Item");
        when(item.getDescription()).thenReturn("Request Description");
        when(item.getAvailable()).thenReturn(true);
        when(item.getRequest()).thenReturn(request);
        when(request.getId()).thenReturn(1001L);

        // Act
        ItemForRequestDto result = ItemMapper.toItemForRequestDto(item);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Request Item", result.getName());
        assertEquals("Request Description", result.getDescription());
        assertTrue(result.getAvailable());
        assertEquals(1001L, result.getRequestId());
    }
}
