package org.arjunaoverdrive.newsapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News implements Authorable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_seq_generator")
    @SequenceGenerator(name = "news_seq_generator", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String heading;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long commentId){
        comments = comments.stream()
                .takeWhile(c -> !c.getId().equals(commentId))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News news)) return false;
        return Objects.equals(getId(), news.getId()) &&
                Objects.equals(getHeading(), news.getHeading()) &&
                Objects.equals(getContent(), news.getContent()) &&
                Objects.equals(getAuthor(), news.getAuthor()) &&
                Objects.equals(getCategory(), news.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHeading(), getContent(), getAuthor(), getCategory());
    }
}
