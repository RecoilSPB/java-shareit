package ru.practicum.user.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserClientTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private UserClient userClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Настройка мока RestTemplateBuilder
        when(restTemplateBuilder.uriTemplateHandler(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        // Инициализация UserClient
        userClient = new UserClient("http://localhost:8080", restTemplateBuilder);
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserDto userDto = new UserDto(1L, "test", "test@example.com");
        ResponseEntity<Object> response = new ResponseEntity<>(userDto, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), eq(org.springframework.http.HttpMethod.POST), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = userClient.createUser(userDto);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(restTemplate, times(1)).exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.POST),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testGetUserById() {
        // Arrange
        long userId = 1L;
        ResponseEntity<Object> response = new ResponseEntity<>("User Data", HttpStatus.OK);
        when(restTemplate.exchange(eq("/" + userId), eq(org.springframework.http.HttpMethod.GET), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = userClient.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User Data", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq("/" + userId),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testUpdateUser() {
        // Arrange
        long userId = 1L;
        UserDto userDto = new UserDto(userId, "updatedName", "updated@example.com");
        ResponseEntity<Object> response = new ResponseEntity<>(userDto, HttpStatus.OK);
        when(restTemplate.exchange(eq("/" + userId), eq(org.springframework.http.HttpMethod.PATCH), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = userClient.updateUser(userId, userDto);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(restTemplate, times(1)).exchange(
                eq("/" + userId),
                eq(org.springframework.http.HttpMethod.PATCH),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        ResponseEntity<Object> response = new ResponseEntity<>("All Users Data", HttpStatus.OK);
        when(restTemplate.exchange(eq(""), eq(org.springframework.http.HttpMethod.GET), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = userClient.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("All Users Data", result.getBody());
        verify(restTemplate, times(1)).exchange(
                eq(""),
                eq(org.springframework.http.HttpMethod.GET),
                any(),
                eq(Object.class)
        );
    }

    @Test
    void testDeleteUserById() {
        // Arrange
        long userId = 1L;
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(restTemplate.exchange(eq("/" + userId), eq(org.springframework.http.HttpMethod.DELETE), any(), eq(Object.class)))
                .thenReturn(response);

        // Act
        ResponseEntity<Object> result = userClient.deleteUserById(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(restTemplate, times(1)).exchange(
                eq("/" + userId),
                eq(org.springframework.http.HttpMethod.DELETE),
                any(),
                eq(Object.class)
        );
    }
}
