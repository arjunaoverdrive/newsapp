package org.arjunaoverdrive.newsapp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.mapper.CategoryMapper;
import org.arjunaoverdrive.newsapp.service.CategoryService;
import org.arjunaoverdrive.newsapp.web.dto.ErrorResponse;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryListResponse;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryRequest;
import org.arjunaoverdrive.newsapp.web.dto.category.CategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category v1", description = "Category API v1.")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(
            summary = "Get all categories.",
            description = "Get a page of categories."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryListResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(categoryMapper.toCategoryListResponse(categoryService.findAllCategories(pageable)));
    }

    @Operation(
            summary = "Get category by id.",
            description = "Get a category by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .body(categoryMapper.toCategoryResponse(categoryService.findCategoryById(id)));
    }

    @Operation(
            summary = "Create category.",
            description = "Create a new category."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.toCategoryResponse(categoryService.createCategory(categoryMapper.toCategory(request))));
    }


    @Operation(
            summary = "Update category.",
            description = "Update an existing category."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")

            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id, @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.accepted()
                .body(categoryMapper.toCategoryResponse(categoryService.updateCategory(categoryMapper.toCategory(id, request))));
    }

    @Operation(
            summary = "Delete category.",
            description = "Delete a category by id."
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "204"
        ),
        @ApiResponse(
                responseCode = "404",
                content = {
                        @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                }
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
