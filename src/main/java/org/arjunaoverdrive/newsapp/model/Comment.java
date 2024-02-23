package org.arjunaoverdrive.newsapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Comment implements Authorable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq_generator")
    @SequenceGenerator(name = "comment_seq_generator", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AppUser author;

    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private News news;

    @Override
    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
