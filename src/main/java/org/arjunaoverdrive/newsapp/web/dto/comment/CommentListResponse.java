package org.arjunaoverdrive.newsapp.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListResponse {
    private List<CommentResponse> comments = new ArrayList<>();
}
