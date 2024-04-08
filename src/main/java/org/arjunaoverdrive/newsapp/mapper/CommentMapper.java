package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.Comment;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentListResponse;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentRequest;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment toComment(CommentRequest request);


    @Mapping(target = "authorId", expression = "java(comment.getAuthor().getId())")
    @Mapping(target = "newsId", expression = "java(comment.getNews().getId())")
    CommentResponse toCommentResponse(Comment comment);

    default CommentListResponse toCommentListResponse(List<Comment> comments){
        CommentListResponse response = new CommentListResponse();
        response.setComments(comments.stream()
                .map(this::toCommentResponse)
                .toList());
        return response;
    }
}
