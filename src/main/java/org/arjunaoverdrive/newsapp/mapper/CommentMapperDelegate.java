package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.Comment;
import org.arjunaoverdrive.newsapp.security.AppUserDetails;
import org.arjunaoverdrive.newsapp.service.NewsService;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class CommentMapperDelegate implements CommentMapper{

    @Autowired
    private  NewsService newsService;

    @Autowired
    private UserService userService;

    @Override
    public Comment toComment(CommentRequest request) {
        Comment comment = Comment.builder()
                .text(request.getText())
                .news( newsService.findById(request.getNewsId()))
                .author(getAuthor())
                .build();
        return comment;
    }

//    @Override
//    public Comment toComment(Long id, CommentUpdateRequest request) {
//        Comment comment = toComment(request);
//        comment.setId(id);
//        comment.setAuthor(userService.findUserByCommentId(id));
//        return comment;
//    }

    private AppUser getAuthor(){
        AppUserDetails principal = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserById(principal.getId());
    }
}
