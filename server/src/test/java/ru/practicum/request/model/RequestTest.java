package ru.practicum.request.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

public class RequestTest {

    @Test
    public void requestEqualityTest() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 23, 58, 00);
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Request r1 = new Request();
        r1.setId(1L);
        r1.setDescription("Запрос");
        r1.setRequestor(u1);
        r1.setCreated(time);

        Request r2 = new Request();
        r2.setId(1L);
        r2.setDescription("Запрос");
        r2.setRequestor(u1);
        r2.setCreated(time);

        Assertions.assertEquals(r1.hashCode(), r2.hashCode());
        Assertions.assertEquals(r1, r2);
    }

    @Test
    public void requestInequalityTest() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 23, 58, 00);
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Request r1 = new Request();
        r1.setId(1L);
        r1.setDescription("Запрос");
        r1.setRequestor(u1);
        r1.setCreated(time);

        Request r2 = new Request();
        r2.setId(2L);
        r2.setDescription("Запрос");
        r2.setRequestor(u1);
        r2.setCreated(time);
        Assertions.assertNotEquals(r1, r2);
    }

    @Test
    public void requestInequalityTestWithNull() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 23, 58, 00);
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Request r1 = new Request();
        r1.setId(null);
        r1.setDescription("Запрос");
        r1.setRequestor(u1);
        r1.setCreated(time);

        Request r2 = new Request();
        r2.setId(2L);
        r2.setDescription("Запрос");
        r2.setRequestor(u1);
        r2.setCreated(time);
        Assertions.assertNotEquals(r1, r2);
    }

}