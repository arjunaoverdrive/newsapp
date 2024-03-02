package org.arjunaoverdrive.newsapp.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private Long newsId;
    private Long authorId;
    private String text;
    private Instant createdAt;
    private Instant updatedAt;
}
