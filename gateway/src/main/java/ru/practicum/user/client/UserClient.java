package ru.practicum.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .build()
        );
    }

    public ResponseEntity<Object> createUser(UserDto userDto) {
        return post(userDto);
    }

    public ResponseEntity<Object> updateUser(long userId, UserDto userDto) {
        return patch("/" + userId, userId, userDto);
    }

    public ResponseEntity<Object> getAllUsers() {
        return get();
    }

    public ResponseEntity<Object> getUserById(long userId) {
        return get("/" + userId, userId);
    }

    public ResponseEntity<Object> deleteUserById(long userId) {
        return delete("/" + userId, userId);
    }
}