package ru.practicum.request.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.request.dto.ItemRequestDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RequestClientTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private RequestClient requestClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Настройка мока RestTemplateBuilder
        when(restTemplateBuilder.uriTemplateHandler(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        // Инициализация RequestClient
        requestClient = new RequestClient("http://localhost:8080", restTemplateBuilder);
    }

    @Test
    void testAddRequest() {
        // Arrange
        long userId = 1L;
        ItemRequestDto itemRequestDto = ItemRequestDto.builder().id(1L).description("description").build();
        ResponseEntity<Object> response = new ResponseEntity<>(itemRequestDto, HttpStatus.CREATED);
        when(restTemplate.exchange(any(String.class), eq(org.springframework.http.HttpMethod.POST), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = requestClient.addRequest(itemRequestDto, userId);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(restTemplate, times(1)).exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testGetRequestsByUser() {
        // Arrange
        long userId = 1L;
        ResponseEntity<Object> response = new ResponseEntity<>("User Requests", HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), eq(org.springframework.http.HttpMethod.GET), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = requestClient.getRequestsByUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User Requests", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testGetAllRequests() {
        // Arrange
        long userId = 1L;
        Integer from = 0;
        Integer size = 10;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("from", from);
        parameters.put("size", size);

        ResponseEntity<Object> response = new ResponseEntity<>("All Requests", HttpStatus.OK);

        when(restTemplate.exchange(
                eq("/all?from={from}&size={size}"),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class),
                eq(parameters)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = requestClient.getAllRequests(userId, from, size);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("All Requests", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/all?from={from}&size={size}"),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class),
                eq(parameters)
        );
    }


    @Test
    void testGetRequestById() {
        // Arrange
        long userId = 1L;
        long itemRequestId = 2L;
        ResponseEntity<Object> response = new ResponseEntity<>("Request Details", HttpStatus.OK);
        when(restTemplate.exchange(eq("/" + itemRequestId), eq(org.springframework.http.HttpMethod.GET), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = requestClient.getRequestById(itemRequestId, userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Request Details", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/" + itemRequestId),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        );
    }
}
