package org.arjunaoverdrive.newsapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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
    private Set<News> newsSet;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();
}
