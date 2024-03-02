package org.arjunaoverdrive.newsapp.web.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsListResponse {
    private List<NewsResponseWOComments> news = new ArrayList<>();
}
