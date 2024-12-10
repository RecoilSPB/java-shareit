package ru.practicum.item.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.user.model.User;

public class ItemTest {

    @Test
    public void itemEqualityTest() {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Петров");
        u1.setEmail("some2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Item i2 = new Item();
        i2.setId(1L);
        i2.setName("Вещь");
        i2.setDescription("Описание");
        i2.setAvailable(true);
        i2.setOwner(u1);
        i2.setRequest(null);

        Assertions.assertEquals(i1.hashCode(), i2.hashCode());
        Assertions.assertEquals(i1, i2);
    }

    @Test
    public void itemInequalityTest() {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Петров");
        u1.setEmail("some2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Item i2 = new Item();
        i2.setId(2L);
        i2.setName("Вещь");
        i2.setDescription("Описание");
        i2.setAvailable(true);
        i2.setOwner(u1);
        i2.setRequest(null);
        Assertions.assertNotEquals(i1, i2);
    }

    @Test
    public void itemInequalityTestWithNull() {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Петров");
        u1.setEmail("some2@email.com");

        Item i1 = new Item();
        i1.setId(null);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Item i2 = new Item();
        i2.setId(2L);
        i2.setName("Вещь");
        i2.setDescription("Описание");
        i2.setAvailable(true);
        i2.setOwner(u1);
        i2.setRequest(null);
        Assertions.assertNotEquals(i1, i2);
    }

}