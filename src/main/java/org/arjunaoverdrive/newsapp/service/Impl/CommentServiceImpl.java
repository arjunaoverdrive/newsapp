package org.arjunaoverdrive.newsapp.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.newsapp.dao.CommentRepository;
import org.arjunaoverdrive.newsapp.exception.CannotSaveEntityException;
import org.arjunaoverdrive.newsapp.exception.EntityNotFoundException;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.Comment;
import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.service.CommentService;
import org.arjunaoverdrive.newsapp.service.NewsService;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.utils.BeanUtils;
import org.arjunaoverdrive.newsapp.web.aop.EditableByAuthor;
import org.arjunaoverdrive.newsapp.web.aop.StillCanEdit;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsService newsService;
    private final UserService userService;



    @Override
    public Comment findCommentById(Long id) {
        log.debug("Getting comment with id {}...", id);
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                MessageFormat.format("Comment with id {0} not found.", id)
                        ));
    }

    @Override
    public Comment createComment(Comment comment) {
        log.debug("Creating comment {}...", comment);

        AppUser user = userService.findUserById(comment.getAuthor().getId());
        comment.setAuthor(user);

        News news = newsService.findNewsById(comment.getNews().getId());
        comment.setNews(news);

        try{
            comment = commentRepository.save(comment);
        }catch (Exception e){
            throw new CannotSaveEntityException(e.getMessage());
        }
        log.debug("Created comment {}.", comment);
        return comment;
    }

    @EditableByAuthor
    @StillCanEdit
    @Override
    public Comment updateComment(Comment comment) {
        log.debug("Updating comment {}...", comment);

        Comment fromDB = findCommentById(comment.getId());
        BeanUtils.copyNonNullProperties(comment, fromDB);
        fromDB.setText(comment.getText());

        try{
            fromDB = commentRepository.save(fromDB);
        }catch (Exception e){
            throw new CannotSaveEntityException(e.getMessage());
        }

        log.debug("Updated comment {}.", comment);
        return fromDB;
    }

    @EditableByAuthor
    @Override
    public void deleteComment(Long id) {
        log.debug("Deleting comment with id {}...", id);

        Comment toDelete = findCommentById(id);
        commentRepository.delete(toDelete);

        log.debug("Deleted comment {}.", toDelete);
    }
}
