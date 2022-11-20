package ru.blog.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@EqualsAndHashCode(exclude = {"author", "inFavorites"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    private LocalDateTime createdAt;
    @Column(length = 20)
    private String title;
    @Column(length = 1000)
    private String text;
    @Enumerated(value = EnumType.STRING)
    private State state;
    @ManyToMany(mappedBy = "favorites")
    private Set<Author> inFavorites;

    public enum State {
        DRAFT, DELETED, PUBLISHED
    }


}
