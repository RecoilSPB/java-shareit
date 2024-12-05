package ru.practicum.mapper.user;

import ru.practicum.dto.user.UserDto;
import ru.practicum.model.user.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder().id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder().id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }
}