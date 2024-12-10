package ru.practicum.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void shouldCreateUserDtoWithValidData() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        assertNotNull(userDto.getId());
        assertEquals(1L, userDto.getId());
        assertEquals("John Doe", userDto.getName());
        assertEquals("john.doe@example.com", userDto.getEmail());
    }

    @Test
    void shouldHandleNullFields() {
        UserDto userDto = UserDto.builder()
                .id(null)
                .name(null)
                .email(null)
                .build();

        assertNull(userDto.getId());
        assertNull(userDto.getName());
        assertNull(userDto.getEmail());
    }

    @Test
    void shouldSetFieldsCorrectly() {
        UserDto userDto = new UserDto();

        userDto.setId(2L);
        userDto.setName("Jane Doe");
        userDto.setEmail("jane.doe@example.com");

        assertEquals(2L, userDto.getId());
        assertEquals("Jane Doe", userDto.getName());
        assertEquals("jane.doe@example.com", userDto.getEmail());
    }

    @Test
    void shouldBuildEmptyUserDto() {
        UserDto userDto = UserDto.builder().build();

        assertNull(userDto.getId());
        assertNull(userDto.getName());
        assertNull(userDto.getEmail());
    }

    @Test
    void shouldTestToString() {
        UserDto userDto = UserDto.builder()
                .id(3L)
                .name("Alice")
                .email("alice@example.com")
                .build();

        String expectedString = "UserDto(id=3, name=Alice, email=alice@example.com)";
        assertEquals(expectedString, userDto.toString());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        UserDto user1 = UserDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        UserDto user2 = UserDto.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        UserDto user3 = UserDto.builder()
                .id(2L)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .build();

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}
