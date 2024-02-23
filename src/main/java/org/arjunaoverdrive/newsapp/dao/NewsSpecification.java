package org.arjunaoverdrive.newsapp.dao;

import org.arjunaoverdrive.newsapp.model.News;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byNewsAuthor(newsFilter.getAuthorId())
                .and(byNewsCategory(newsFilter.getCategoryId())));
    }

    static Specification<News> byNewsAuthor(Long authorId){
        return (root, query, builder) -> {
            if(authorId == null){
                return null;
            }
            return builder.equal(root.get("author").get("id"), authorId);
        };
    }

    static Specification<News> byNewsCategory(Integer categoryId){
        return (root, query, builder) -> {
            if(categoryId == null) {
                return null;
            }
            return builder.equal(root.get("category").get("id"), categoryId);
        };
    }



}
