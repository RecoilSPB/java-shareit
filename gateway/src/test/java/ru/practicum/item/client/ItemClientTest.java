package ru.practicum.item.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.item.comment.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ItemClientTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private ItemClient itemClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        when(restTemplateBuilder.uriTemplateHandler(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        itemClient = new ItemClient("http://localhost:8080", restTemplateBuilder);
    }

    @Test
    void testCreateItem() {
        // Arrange
        long userId = 1L;
        ItemDto itemDto = new ItemDto();
        ResponseEntity<Object> response = new ResponseEntity<>("Item Created", HttpStatus.CREATED);

        when(restTemplate.exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = itemClient.creatItem(userId, itemDto);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Item Created", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testUpdateItem() {
        // Arrange
        long itemId = 1L;
        long userId = 2L;
        ItemDto itemDto = new ItemDto();
        ResponseEntity<Object> response = new ResponseEntity<>("Item Updated", HttpStatus.OK);

        when(restTemplate.exchange(
                eq("/" + itemId),
                eq(org.springframework.http.HttpMethod.PATCH),
                any(),
                eq(Object.class)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = itemClient.updateItem(itemId, userId, itemDto);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Item Updated", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/" + itemId),
                eq(org.springframework.http.HttpMethod.PATCH),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testGetItemById() {
        // Arrange
        long itemId = 1L;
        long userId = 2L;
        ResponseEntity<Object> response = new ResponseEntity<>("Item Details", HttpStatus.OK);

        when(restTemplate.exchange(
                eq("/" + itemId),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = itemClient.getItemById(itemId, userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Item Details", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/" + itemId),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testGetItemsByUser() {
        // Arrange
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("from", from);
        parameters.put("size", size);

        ResponseEntity<Object> response = new ResponseEntity<>("User Items", HttpStatus.OK);

        when(restTemplate.exchange(
                eq("?from={from}&size={size}"),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class),
                eq(parameters)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = itemClient.getItemsByUser(userId, from, size);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User Items", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("?from={from}&size={size}"),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class),
                eq(parameters)
        );
    }

    @Test
    void testAddComment() {
        // Arrange
        long itemId = 1L;
        long authorId = 2L;
        CommentDto commentDto = new CommentDto();
        ResponseEntity<Object> response = new ResponseEntity<>("Comment Added", HttpStatus.CREATED);

        when(restTemplate.exchange(
                eq("/" + itemId + "/comment"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = itemClient.addComment(itemId, authorId, commentDto);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Comment Added", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/" + itemId + "/comment"),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        );
    }
}
