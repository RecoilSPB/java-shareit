package ru.practicum.shareit.user.service;

import ru.practicum.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(Long id);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Long id);

    void deleteUser(Long id);

}