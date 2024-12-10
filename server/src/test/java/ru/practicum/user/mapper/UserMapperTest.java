package ru.practicum.user.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void toUserDto_shouldMapCorrectly() {
        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        UserDto result = UserMapper.toUserDto(user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getName()).isEqualTo(user.getName());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void toUser_shouldMapCorrectly() {
        UserDto dto = UserDto.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .build();

        User result = UserMapper.toUser(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dto.getId());
        assertThat(result.getName()).isEqualTo(dto.getName());
        assertThat(result.getEmail()).isEqualTo(dto.getEmail());
    }
}
