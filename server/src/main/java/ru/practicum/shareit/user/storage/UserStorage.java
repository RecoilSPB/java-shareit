package ru.practicum.shareit.user.storage;


import ru.practicum.model.user.User;

import java.util.List;

public interface UserStorage {
    List<User> getUsers();

    User getUserById(Long id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    User checkUserAvailability(String operation, Long id);
}