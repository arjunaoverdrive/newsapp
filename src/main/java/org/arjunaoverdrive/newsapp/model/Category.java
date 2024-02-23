package org.arjunaoverdrive.newsapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_generator")
    @SequenceGenerator(name = "category_seq_generator", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<News> news;

    public void addNews(News newsItem) {
        news.add(newsItem);
    }

    public void removeNews(Long newsId) {
        news = news.stream()
                .takeWhile(n -> !n.getId().equals(newsId))
                .collect(Collectors.toSet());
    }

}
