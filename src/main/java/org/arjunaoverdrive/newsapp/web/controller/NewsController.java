package org.arjunaoverdrive.newsapp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.mapper.NewsMapper;
import org.arjunaoverdrive.newsapp.service.NewsService;
import org.arjunaoverdrive.newsapp.web.dto.ErrorResponse;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsFilter;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsListResponse;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsRequest;
import org.arjunaoverdrive.newsapp.web.dto.news.NewsResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "News v1", description = "News API v1")
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @Operation(
            summary = "Get all news.",
            description = "Get a page of news"

    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = NewsListResponse.class), mediaType = "application/json")
            }
    )
    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return ResponseEntity.ok().body(newsMapper.toNewsListResponse(newsService.findAllNews(pageable)));
    }


    @Operation(
            summary = "Get news by id.",
            description = "Get news by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
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
    public ResponseEntity<NewsResponse> findNewsById(@PathVariable Long id) {
        return ResponseEntity.ok().body(newsMapper.toNewsResponse(newsService.findById(id)));
    }

    @Operation(
            summary = "Filter news by author and/or category.",
            description = "Get page of news filtered by author and category."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsListResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    }
    )
    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterNewsBy(
            @Valid NewsFilter newsFilter,
            @PageableDefault(sort = "author", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok()
                .body(newsMapper.toNewsListResponse(newsService.filterBy(newsFilter, pageable)));
    }

    @Operation(
            summary = "Create news.",
            description = "Create new news item."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
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
    public ResponseEntity<NewsResponse> createNews(@RequestBody @Valid NewsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.toNewsResponse(newsService.createNews(newsMapper.toNews(request))));
    }

    @Operation(
            summary = "Update news.",
            description = "Update an existing news item."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/type")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable Long id, @RequestBody @Valid NewsRequest request) {
        return ResponseEntity.accepted()
                .body(newsMapper.toNewsResponse(newsService.updateNewsById(id, newsMapper.toNews( request))));
    }

    @Operation(
            summary = "Delete news by id.",
            description = "Delete a news item by id"
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
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
