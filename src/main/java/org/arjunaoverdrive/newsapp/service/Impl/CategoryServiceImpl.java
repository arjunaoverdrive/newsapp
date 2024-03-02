package org.arjunaoverdrive.newsapp.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.newsapp.dao.CategoryRepository;
import org.arjunaoverdrive.newsapp.exception.CannotSaveEntityException;
import org.arjunaoverdrive.newsapp.exception.EntityNotFoundException;
import org.arjunaoverdrive.newsapp.model.Category;
import org.arjunaoverdrive.newsapp.service.CategoryService;
import org.arjunaoverdrive.newsapp.utils.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> findAllCategories(Pageable pageable) {
        log.debug("Getting all categories...");
        return categoryRepository.findAll(pageable).getContent();
    }

    @Override
    public Category findCategoryById(Integer id) {
        log.debug("Getting category with id {}...", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Category with id {0} not found.", id)
                ));
    }

    @Override
    public Category createCategory(Category category) {
        log.debug("Saving category {}...", category);
        try{
            category = categoryRepository.save(category);
        }catch (Exception e){
            throw new CannotSaveEntityException(e.getMessage());
        }
        log.debug("Returning saved category {}.", category);
        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        Category fromDb = findCategoryById(category.getId());
        log.debug("Updating category {}...", fromDb);
        BeanUtils.copyNonNullProperties(category, fromDb);
        try {
            fromDb = categoryRepository.save(fromDb);
        }catch (Exception e){
            throw new CannotSaveEntityException(e.getMessage());
        }
        log.debug("Returning updated category {}.", category);
        return fromDb;
    }

    @Override
    public void deleteCategoryById(Integer id) {
        Category toDelete = findCategoryById(id);
        log.debug("Deleting category {}...", toDelete);
        categoryRepository.delete(toDelete);
        log.debug("Deleted category {}.", toDelete);
    }
}
