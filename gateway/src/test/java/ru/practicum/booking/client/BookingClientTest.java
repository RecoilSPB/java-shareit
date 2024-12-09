package ru.practicum.booking.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.booking.dto.BookItemRequestDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BookingClientTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private BookingClient bookingClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        when(restTemplateBuilder.uriTemplateHandler(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        bookingClient = new BookingClient("http://localhost:8080", restTemplateBuilder);
    }

    @Test
    void testGetAllBookings() {
        // Arrange
        long userId = 1L;
        String state = "ALL";
        Integer from = 0;
        Integer size = 10;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("state", state);
        parameters.put("from", from);
        parameters.put("size", size);

        ResponseEntity<Object> response = new ResponseEntity<>("Bookings List", HttpStatus.OK);

        when(restTemplate.exchange(
                eq("?state={state}&from={from}&size={size}"),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class),
                eq(parameters)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = bookingClient.getAllBookings(userId, state, from, size);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Bookings List", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("?state={state}&from={from}&size={size}"),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class),
                eq(parameters)
        );
    }

    @Test
    void testCreateBooking() {
        // Arrange
        long userId = 1L;
        BookItemRequestDto requestDto = new BookItemRequestDto();
        ResponseEntity<Object> response = new ResponseEntity<>("Booking Created", HttpStatus.CREATED);

        when(restTemplate.exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = bookingClient.createBooking(userId, requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Booking Created", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testGetBookingInfo() {
        // Arrange
        long userId = 1L;
        Long bookingId = 123L;
        ResponseEntity<Object> response = new ResponseEntity<>("Booking Info", HttpStatus.OK);

        when(restTemplate.exchange(
                eq("/" + bookingId),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = bookingClient.getBookingInfo(userId, bookingId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Booking Info", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/" + bookingId),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testUpdateApprove() {
        // Arrange
        long userId = 1L;
        long bookingId = 123L;
        boolean approved = true;
        ResponseEntity<Object> response = new ResponseEntity<>("Approval Updated", HttpStatus.OK);

        when(restTemplate.exchange(
                eq("/" + bookingId + "?approved=" + approved),
                eq(org.springframework.http.HttpMethod.PATCH),
                any(),
                eq(Object.class)
        )).thenReturn(response);

        // Act
        ResponseEntity<Object> result = bookingClient.updateApprove(bookingId, approved, userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Approval Updated", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/" + bookingId + "?approved=" + approved),
                eq(org.springframework.http.HttpMethod.PATCH),
                any(),
                eq(Object.class)
        );
    }
}
