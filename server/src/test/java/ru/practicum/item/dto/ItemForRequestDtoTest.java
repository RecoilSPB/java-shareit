package ru.practicum.item.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemForRequestDtoTest {

    @Test
    void shouldCreateItemForRequestDtoWithValidData() {
        ItemForRequestDto dto = ItemForRequestDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .requestId(100L)
                .build();

        assertEquals(1L, dto.getId());
        assertEquals("Drill", dto.getName());
        assertEquals("A power drill", dto.getDescription());
        assertTrue(dto.getAvailable());
        assertEquals(100L, dto.getRequestId());
    }

    @Test
    void shouldHandleNullFields() {
        ItemForRequestDto dto = ItemForRequestDto.builder()
                .id(null)
                .name(null)
                .description(null)
                .available(null)
                .requestId(null)
                .build();

        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getDescription());
        assertNull(dto.getAvailable());
        assertNull(dto.getRequestId());
    }

    @Test
    void shouldSetFieldsCorrectly() {
        ItemForRequestDto dto = new ItemForRequestDto();

        dto.setId(2L);
        dto.setName("Hammer");
        dto.setDescription("A heavy hammer");
        dto.setAvailable(false);
        dto.setRequestId(200L);

        assertEquals(2L, dto.getId());
        assertEquals("Hammer", dto.getName());
        assertEquals("A heavy hammer", dto.getDescription());
        assertFalse(dto.getAvailable());
        assertEquals(200L, dto.getRequestId());
    }

    @Test
    void shouldTestToString() {
        ItemForRequestDto dto = ItemForRequestDto.builder()
                .id(3L)
                .name("Saw")
                .description("A hand saw")
                .available(true)
                .requestId(300L)
                .build();

        String expectedString = "ItemForRequestDto(id=3, name=Saw, description=A hand saw, available=true, requestId=300)";
        assertEquals(expectedString, dto.toString());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        ItemForRequestDto dto1 = ItemForRequestDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .requestId(100L)
                .build();

        ItemForRequestDto dto2 = ItemForRequestDto.builder()
                .id(1L)
                .name("Drill")
                .description("A power drill")
                .available(true)
                .requestId(100L)
                .build();

        ItemForRequestDto dto3 = ItemForRequestDto.builder()
                .id(2L)
                .name("Hammer")
                .description("A heavy hammer")
                .available(false)
                .requestId(200L)
                .build();

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
