package org.arjunaoverdrive.newsapp.mapper;

import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsListResponse;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsRequest;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsResponse;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsResponseWOComments;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class,
        CommentMapper.class})
public interface NewsMapper {

    News toNews(NewsRequest request);

    News toNews(Long id, NewsRequest request);

    NewsResponse toNewsResponse(News news);

    @Mapping(target = "comments", expression = "java(news.getComments().size())")
    NewsResponseWOComments toNewsResponseWOComments(News news);


    default NewsListResponse toNewsListResponse(List<News> newsList){
        NewsListResponse response = new NewsListResponse();

        response.setNews(newsList.stream()
                .map(this::toNewsResponseWOComments)
                .toList());

        return response;
    }
}
