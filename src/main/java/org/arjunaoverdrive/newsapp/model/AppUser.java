package org.arjunaoverdrive.newsapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 65)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 65)
    private String lastName;

    @Column(nullable = false, length = 65, unique = true)
    private String email;

    @Column(nullable = false, length = 65)
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<News> newsSet = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments = new HashSet<>();

    public void addNews(News news){
        newsSet.add(news);
    }

    public void removeNews(Long newsId){
        newsSet = newsSet.stream()
                .takeWhile(n -> !n.getId().equals(newsId))
                .collect(Collectors.toSet());
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long commentId){
        comments = comments.stream()
                .takeWhile(c -> !c.getId().equals(commentId))
                .collect(Collectors.toSet());
    }
}
