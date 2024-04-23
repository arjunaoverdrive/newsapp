package org.arjunaoverdrive.newsapp.service;

import org.arjunaoverdrive.newsapp.model.Comment;
import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
public interface NewsService extends AuthorableService {
    List<News> findAllNews(Pageable pageable);

    News findById(Long id);

    News createNews(News news);

    News updateNewsById(Long id, News news);

    void deleteById(Long id);

    List<Comment> findAllCommentsByNewsId(Long newsId);

    List<News> filterBy(NewsFilter newsFilter, Pageable pageable);
}
