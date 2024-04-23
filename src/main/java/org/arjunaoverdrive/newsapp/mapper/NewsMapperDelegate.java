package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.security.AppUserDetails;
import org.arjunaoverdrive.newsapp.service.CategoryService;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

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
        news.setCategory(categoryService.findCategoryById(request.getCategoryId()));

        news.setAuthor(getAuthor());
        return news;
    }


    private AppUser getAuthor() {
        AppUserDetails principal = (AppUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userService.findUserById(principal.getId());
    }
}
