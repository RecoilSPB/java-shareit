package ru.practicum.user.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void userEqualityTest() {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        User u2 = new User();
        u2.setId(1L);
        u2.setName("Popov");
        u2.setEmail("test2@email.com");

        Assertions.assertEquals(u1.hashCode(), u2.hashCode());
        Assertions.assertEquals(u1, u2);
    }

    @Test
    public void userInequalityTest() {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        User u2 = new User();
        u2.setId(2L);
        u2.setName("Иванов");
        u2.setEmail("test2@email.com");

        Assertions.assertNotEquals(u1, u2);
    }

    @Test
    public void userInequalityTestWithNull() {
        User u1 = new User();
        u1.setId(null);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        User u2 = new User();
        u2.setId(1L);
        u2.setName("Иванов");
        u2.setEmail("test2@email.com");

        Assertions.assertNotEquals(u1, u2);
    }
}