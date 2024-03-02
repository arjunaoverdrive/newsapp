package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.service.CategoryService;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public News toNews(NewsRequest request) {
        News news = new News();
        news.setContent(request.getContent());
        news.setHeading(request.getHeading());
        news.setAuthor(userService.findUserById(request.getUserId()));
        news.setCategory(categoryService.findCategoryById(request.getCategoryId()));

        return news;
    }

    @Override
    public News toNews(Long id, NewsRequest request) {
        News news = toNews(request);
        news.setId(id);
        return news;
    }
}
