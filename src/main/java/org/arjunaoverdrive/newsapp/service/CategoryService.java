package org.arjunaoverdrive.newsapp.service;

import org.arjunaoverdrive.newsapp.model.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(Pageable pageable);

    Category findById(Integer id);

    Category create(Category category);

    Category update(Category category);

    void deleteById(Integer id);
}
