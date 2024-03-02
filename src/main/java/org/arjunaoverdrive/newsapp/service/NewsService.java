package org.arjunaoverdrive.newsapp.service;

import org.arjunaoverdrive.newsapp.model.Comment;
import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
    List<News> findAllNews(Pageable pageable);

    News findNewsById(Long id);

    News createNews(News news);

    News updateNewsById(News news);

    void deleteNewsById(Long id);

    List<Comment> findAllCommentsByNewsId(Long newsId);

    List<News> filterBy(NewsFilter newsFilter, Pageable pageable);
}
