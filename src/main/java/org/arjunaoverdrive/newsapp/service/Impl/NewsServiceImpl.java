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
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.utils.BeanUtils;
import org.arjunaoverdrive.newsapp.web.aop.EditableByAuthor;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    @Override
    public List<News> findAllNews(Pageable pageable) {
        log.debug("Getting page of news...");
        return newsRepository.findAll(pageable).getContent();
    }

    @Override
    public News findNewsById(Long id) {
        log.debug("Getting news by id {}...", id);
        return newsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format("News with id {0} not found.", id))
                );
    }

    @Override
    public News createNews(News news) {
        log.debug("Creating news {}...", news);

        AppUser user = userService.findUserById(news.getAuthor().getId());
        news.setAuthor(user);

        Category category = categoryService.findCategoryById(news.getCategory().getId());
        news.setCategory(category);
        try{
            news = newsRepository.save(news);
        }catch (Exception e){
            log.warn(e.getMessage());
            throw new CannotSaveEntityException(e.getMessage());
        }
        return news;
    }

    @EditableByAuthor
    @Override
    public News updateNewsById(News news) {
        log.debug("Updating news {}...", news);

        News fromDb = findNewsById(news.getId());
        BeanUtils.copyNonNullProperties(news,fromDb);

        try{
            news = newsRepository.save(news);
        }catch (Exception e){
            throw new CannotSaveEntityException(e.getMessage());
        }

        return news;
    }

    @EditableByAuthor
    @Override
    public void deleteNewsById(Long id) {
        log.debug("Deleting news with id {}...", id);

        News toDelete = findNewsById(id);
        newsRepository.delete(toDelete);

        log.debug("Deleted news {}.", toDelete);
    }

    @Override
    public List<Comment> findAllCommentsByNewsId(Long newsId) {
        News news = findNewsById(newsId);
        return new ArrayList<>(news.getComments());
    }

    @Override
    public List<News> filterBy(NewsFilter newsFilter, Pageable pageable) {
        log.debug("Getting news filtered by filter {}.", newsFilter);

        Specification<News> newsSpecification = NewsSpecification.withFilter(newsFilter);
        return newsRepository.findAll(newsSpecification, pageable).getContent();
    }
}
