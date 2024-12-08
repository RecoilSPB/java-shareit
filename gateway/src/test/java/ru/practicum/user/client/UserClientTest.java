package ru.practicum.user.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.user.UserDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.mockito.Mockito;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserClientTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserClient userClient;

    private UserDto userDto;

    @Value("${shareit-server.url}")
    private String serverUrl = "http://localhost:8080"; // mock URL

    @BeforeEach
    public void setUp() {
        userDto = new UserDto(1L, "John Doe", "john.doe@example.com");

        // Mock the behavior of RestTemplateBuilder to return the mocked RestTemplate
        when(restTemplateBuilder.uriTemplateHandler(any(DefaultUriBuilderFactory.class)))
                .thenReturn(restTemplateBuilder);

        // Explicitly use Supplier<ClientHttpRequestFactory>
        when(restTemplateBuilder.requestFactory(Mockito.<Supplier<ClientHttpRequestFactory>>any()))
                .thenReturn(restTemplateBuilder);

        when(restTemplateBuilder.build())
                .thenReturn(restTemplate);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        when(restTemplate.postForEntity(anyString(), eq(userDto), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        // Act
        ResponseEntity<Object> response = userClient.createUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).postForEntity(anyString(), eq(userDto), eq(Object.class));
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        long userId = 1L;
        when(restTemplate.patchForObject(anyString(), eq(userDto), eq(Object.class), eq(userId)))
                .thenReturn(new Object());

        // Act
        ResponseEntity<Object> response = userClient.updateUser(userId, userDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).patchForObject(anyString(), eq(userDto), eq(Object.class), eq(userId));
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        when(restTemplate.getForEntity(anyString(), eq(Object.class)))
                .thenReturn(ResponseEntity.ok().build());

        // Act
        ResponseEntity<Object> response = userClient.getAllUsers();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(Object.class));
    }

    @Test
    public void testGetUserById() {
        // Arrange
        long userId = 1L;
        when(restTemplate.getForEntity(anyString(), eq(Object.class), eq(userId)))
                .thenReturn(ResponseEntity.ok().build());

        // Act
        ResponseEntity<Object> response = userClient.getUserById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(Object.class), eq(userId));
    }

    @Test
    public void testDeleteUserById() {
        // Arrange
        long userId = 1L;
        doNothing().when(restTemplate).delete(anyString(), eq(userId));

        // Act
        ResponseEntity<Object> response = userClient.deleteUserById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).delete(anyString(), eq(userId));
    }
}
