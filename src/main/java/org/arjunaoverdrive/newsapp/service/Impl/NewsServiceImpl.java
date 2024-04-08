package org.arjunaoverdrive.newsapp.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.newsapp.dao.NewsRepository;
import org.arjunaoverdrive.newsapp.dao.NewsSpecification;
import org.arjunaoverdrive.newsapp.exception.CannotSaveEntityException;
import org.arjunaoverdrive.newsapp.exception.EntityNotFoundException;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.Category;
import org.arjunaoverdrive.newsapp.model.Comment;
import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.service.CategoryService;
import org.arjunaoverdrive.newsapp.service.NewsService;
import org.arjunaoverdrive.newsapp.utils.BeanUtils;
import org.arjunaoverdrive.newsapp.web.aop.CanDeleteEntity;
import org.arjunaoverdrive.newsapp.web.aop.EditableByAuthor;
import org.arjunaoverdrive.newsapp.web.aop.StillCanEdit;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final CategoryService categoryService;

    @Override
    public List<News> findAllNews(Pageable pageable) {
        log.debug("Getting page of news...");
        return newsRepository.findAll(pageable).getContent();
    }

    @Override
    public News findById(Long id) {
        log.debug("Getting news by id {}...", id);
        News news = newsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("News with id {0} not found.", id))
                );
        return news;
    }

    @Override
    public News createNews(News news) {
        log.debug("Creating news {}...", news);

        Category category = categoryService.findCategoryById(news.getCategory().getId());
        news.setCategory(category);

        try {
            news = newsRepository.saveAndFlush(news);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new CannotSaveEntityException(e.getMessage());
        }

        log.debug("Created news {}.", news);
        return news;
    }

    @Override
    @EditableByAuthor
    @StillCanEdit
    public News updateNewsById(Long id, News news) {
        log.debug("Updating news {}...", news);

        News fromDb = findById(id);
        BeanUtils.copyNonNullProperties(news, fromDb);

        try {
            news = newsRepository.save(fromDb);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new CannotSaveEntityException(e.getMessage());
        }

        log.debug("Updated news {}.", news);
        return news;
    }


    @CanDeleteEntity
    @StillCanEdit
    @Override
    public void deleteById(Long id) {
        log.debug("Deleting news with id {}...", id);

        News authorable = findById(id);
        newsRepository.delete(authorable);

        log.debug("Deleted news {}.", authorable);
    }

    @Override
    public List<Comment> findAllCommentsByNewsId(Long newsId) {
        News news = findById(newsId);
        return new ArrayList<>(news.getComments());
    }

    @Override
    public List<News> filterBy(NewsFilter newsFilter, Pageable pageable) {
        log.debug("Getting news filtered by filter {}.", newsFilter);

        Specification<News> newsSpecification = NewsSpecification.withFilter(newsFilter);
        return newsRepository.findAll(newsSpecification, pageable).getContent();
    }

    @Override
    public Instant getCreatedAtById(Long id) {
        return findById(id).getCreatedAt();
    }

    @Override
    public AppUser getAuthor(Long entityId) {
        return findById(entityId).getAuthor();
    }
}
