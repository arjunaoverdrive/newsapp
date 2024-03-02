package org.arjunaoverdrive.newsapp.web.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {
    private String content;
    private String heading;
    private Long userId;
    private Integer categoryId;
}
