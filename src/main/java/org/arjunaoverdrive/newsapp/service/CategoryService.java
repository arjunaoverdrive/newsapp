package org.arjunaoverdrive.newsapp.service;

import org.arjunaoverdrive.newsapp.model.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories(Pageable pageable);

    Category findCategoryById(Integer id);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategoryById(Integer id);
}
