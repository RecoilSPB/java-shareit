package ru.practicum.model.item.comment;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.model.item.Item;
import ru.practicum.model.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = {"item", "author"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private LocalDateTime created;
}
