package org.arjunaoverdrive.newsapp.web.dto.news;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.newsapp.validation.annotations.NewsFilterValid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NewsFilterValid
public class NewsFilter {

    @Positive(message = "CategoryId must be a positive integer value.")
    private Integer categoryId;
    @Positive(message = "AuthorId must be a positive integer value.")
    private Long authorId;
}
