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
@EqualsAndHashCode
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

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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
    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
