package org.arjunaoverdrive.newsapp.service;

import org.arjunaoverdrive.newsapp.model.Comment;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CommentService extends AuthorableService {

    Comment findById(Long id);

    Comment createComment(Comment comment);

    Comment updateComment(Long id, Comment comment);

    void deleteById(Long id);
}
