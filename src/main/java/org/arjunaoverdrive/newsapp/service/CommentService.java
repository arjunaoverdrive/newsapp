package org.arjunaoverdrive.newsapp.service;

import org.arjunaoverdrive.newsapp.model.Comment;

import java.util.List;

public interface CommentService {

    Comment findCommentById(Long id);

    Comment createComment(Comment comment);

    Comment updateComment(Comment comment);

    void deleteComment(Long id);
}
