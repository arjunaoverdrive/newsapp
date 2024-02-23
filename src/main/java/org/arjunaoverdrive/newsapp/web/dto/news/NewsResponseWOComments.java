package org.arjunaoverdrive.newsapp.web.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserResponse;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseWOComments {
    private Long id;
    private String content;
    private UserResponse author;
    private CategoryResponse category;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer comments;
}
