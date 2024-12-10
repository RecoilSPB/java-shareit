package ru.practicum.item.comment.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

public class CommentTest {

    @Test
    public void requestEqualityTest() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 23, 58, 00);

        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Comment c1 = new Comment();
        c1.setId(1L);
        c1.setText("Коммент");
        c1.setItem(i1);
        c1.setAuthor(u1);
        c1.setCreated(time);

        Comment c2 = new Comment();
        c2.setId(1L);
        c2.setText("Коммент");
        c2.setItem(i1);
        c2.setAuthor(u1);
        c2.setCreated(time);

        Assertions.assertEquals(c1.hashCode(), c2.hashCode());
        Assertions.assertEquals(c1, c2);
    }

    @Test
    public void requestInequalityTest() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 23, 58, 00);

        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Comment c1 = new Comment();
        c1.setId(1L);
        c1.setText("Коммент");
        c1.setItem(i1);
        c1.setAuthor(u1);
        c1.setCreated(time);

        Comment c2 = new Comment();
        c2.setId(2L);
        c2.setText("Коммент");
        c2.setItem(i1);
        c2.setAuthor(u1);
        c2.setCreated(time);

        Assertions.assertNotEquals(c1, c2);
    }

    @Test
    public void requestInequalityTestWithNull() {
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 23, 58, 00);

        User u1 = new User();
        u1.setId(1L);
        u1.setName("Popov");
        u1.setEmail("test2@email.com");

        Item i1 = new Item();
        i1.setId(1L);
        i1.setName("Вещь");
        i1.setDescription("Описание");
        i1.setAvailable(true);
        i1.setOwner(u1);
        i1.setRequest(null);

        Comment c1 = new Comment();
        c1.setId(null);
        c1.setText("Коммент");
        c1.setItem(i1);
        c1.setAuthor(u1);
        c1.setCreated(time);

        Comment c2 = new Comment();
        c2.setId(2L);
        c2.setText("Коммент");
        c2.setItem(i1);
        c2.setAuthor(u1);
        c2.setCreated(time);

        Assertions.assertNotEquals(c1, c2);
    }

}