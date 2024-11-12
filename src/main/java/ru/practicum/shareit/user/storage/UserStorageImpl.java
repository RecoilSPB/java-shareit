package ru.practicum.shareit.user.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.DuplicateObjectException;
import ru.practicum.shareit.exception.NotFoundObjectException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Slf4j
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<String, User> usersEmail = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public List<User> getUsers() {
        log.info("Получен запрос на вывод всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Long id) {
        log.info("Получен запрос на вывод пользователя по id {}", id);
        User user = checkUserAvailability("найти", id);
        log.info("Пользователь с id {} найден", id);
        return user;
    }

    @Override
    public void addUser(User user) {
        log.info("Получен запрос на добавление пользователя");
        checkEmailUniqueness(user.getEmail());
        user.setId(generateId());
        users.put(user.getId(), user);
        usersEmail.put(user.getEmail(), user);
        log.info("Пользователь с id {} успешно добавлен", user.getId());
    }

    @Override
    public void updateUser(User user) {
        log.info("Получен запрос на изменение пользователя с id {}", user.getId());
        User existingUser = checkUserAvailability("изменить", user.getId());
        String oldEmail = existingUser.getEmail();

        if (user.getName() != null && !user.getName().isBlank()) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().equals(oldEmail)) {
            checkEmailUniqueness(user.getEmail());
            existingUser.setEmail(user.getEmail());
            usersEmail.remove(oldEmail);
            usersEmail.put(existingUser.getEmail(), existingUser);
        }
        log.info("Пользователь с id {} успешно обновлен", user.getId());
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Получен запрос на удаление пользователя с id {}", id);
        User user = checkUserAvailability("удалить", id);
        usersEmail.remove(user.getEmail());
        users.remove(id);
        log.info("Пользователь с id {} успешно удален", id);
    }

    @Override
    public User checkUserAvailability(String operation, Long id) {
        User user = users.get(id);
        if (user == null) {
            String message = String.format("Невозможно %s. Пользователь с id %d отсутствует!", operation, id);
            log.warn(message);
            throw new NotFoundObjectException(message);
        }
        return user;
    }

    private void checkEmailUniqueness(String email) {
        if (usersEmail.containsKey(email)) {
            String message = "Пользователь с таким email уже существует";
            log.warn(message);
            throw new DuplicateObjectException(message);
        }
    }

    private long generateId() {
        return id.getAndIncrement();
    }
}
