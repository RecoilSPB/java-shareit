package ru.practicum.request.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.item.dto.ItemForRequestDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestDtoTest {

    @Test
    void shouldCreateRequestDtoWithValidData() {
        RequestDto requestDto = RequestDto.builder()
                .id(1L)
                .description("Valid description")
                .created(LocalDateTime.now())
                .items(Collections.emptyList())
                .build();

        assertNotNull(requestDto.getId());
        assertEquals("Valid description", requestDto.getDescription());
        assertNotNull(requestDto.getCreated());
        assertTrue(requestDto.getItems().isEmpty());
    }

    @Test
    void shouldHandleNullItemsList() {
        RequestDto requestDto = RequestDto.builder()
                .id(1L)
                .description("Valid description")
                .created(LocalDateTime.now())
                .items(null)
                .build();

        assertNull(requestDto.getItems());
    }

    @Test
    void shouldHandleItemsListWithValues() {
        ItemForRequestDto item = ItemForRequestDto.builder()
                .id(1L)
                .name("Item Name")
                .description("Item Description")
                .available(true)
                .requestId(1L)
                .build();

        RequestDto requestDto = RequestDto.builder()
                .id(1L)
                .description("Valid description")
                .created(LocalDateTime.now())
                .items(List.of(item))
                .build();

        assertNotNull(requestDto.getItems());
        assertEquals(1, requestDto.getItems().size());
        assertEquals(item, requestDto.getItems().get(0));
    }
}
