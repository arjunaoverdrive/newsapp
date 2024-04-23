package org.arjunaoverdrive.newsapp.dao;

import org.arjunaoverdrive.newsapp.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    Page<AppUser> findAll(Pageable pageable);

    Optional<AppUser> findByEmail(String username);

    boolean existsByEmail(String email);

}
