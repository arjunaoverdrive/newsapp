package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.Comment;
import org.arjunaoverdrive.newsapp.service.NewsService;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper{

    @Autowired
    private  UserService userService;
    @Autowired
    private  NewsService newsService;

    @Override
    public Comment toComment(CommentRequest request) {
        Comment comment = Comment.builder()
                .text(request.getText())
                .author(userService.findUserById(request.getUserId()))
                .news(newsService.findNewsById(request.getNewsId()))
                .build();
        return comment;
    }

    @Override
    public Comment toComment(Long id, CommentRequest request) {
        Comment comment = toComment(request);
        comment.setId(id);
        return comment;
    }
}
