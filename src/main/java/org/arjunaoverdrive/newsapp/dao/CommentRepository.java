package org.arjunaoverdrive.newsapp.dao;

import org.arjunaoverdrive.newsapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
