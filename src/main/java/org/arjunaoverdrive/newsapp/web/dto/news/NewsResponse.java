package org.arjunaoverdrive.newsapp.web.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryResponse;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserResponse;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String content;
    private String heading;
    private UserResponse author;
    private CategoryResponse category;
    private Set<CommentResponse> comments;
}
