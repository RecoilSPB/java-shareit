package ru.practicum.request.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.request.client.RequestClient;
import ru.practicum.request.dto.RequestDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class RequestControllerTest {

    @Mock
    private RequestClient requestClient;

    @InjectMocks
    private RequestController requestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetYourRequests() {
        Long userId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(requestClient.getRequestsByUser(eq(userId))).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.getYourRequests(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddRequest() {
        Long userId = 1L;
        RequestDto requestDto = new RequestDto();
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(requestClient.addRequest(eq(requestDto), eq(userId))).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.addRequest(userId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllRequests() {
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(requestClient.getAllRequests(eq(userId), eq(from), eq(size))).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.getAllRequests(userId, from, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetRequestById() {
        Long userId = 1L;
        Long requestId = 1L;
        ResponseEntity<Object> expectedResponse = ResponseEntity.ok().build();

        when(requestClient.getRequestById(eq(requestId), eq(userId))).thenReturn(expectedResponse);

        ResponseEntity<Object> response = requestController.getRequestById(userId, requestId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}