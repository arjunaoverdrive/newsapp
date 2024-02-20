package org.arjunaoverdrive.newsapp.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.mapper.CategoryMapper;
import org.arjunaoverdrive.newsapp.service.CategoryService;
import org.arjunaoverdrive.newsapp.web.dto.CategoryListResponse;
import org.arjunaoverdrive.newsapp.web.dto.CategoryRequest;
import org.arjunaoverdrive.newsapp.web.dto.CategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(Pageable pageable){
        return ResponseEntity.ok().body(categoryMapper.toCategoryListResponse(categoryService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable Integer id){
        return ResponseEntity.ok()
                .body(categoryMapper.toCategoryResponse(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.toCategoryResponse(categoryService.create(categoryMapper.toCategory(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id, @RequestBody @Valid CategoryRequest request){
        return ResponseEntity.accepted()
                .body(categoryMapper.toCategoryResponse(categoryService.update(categoryMapper.toCategory(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){
        categoryService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
