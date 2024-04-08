package org.arjunaoverdrive.newsapp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.mapper.CommentMapper;
import org.arjunaoverdrive.newsapp.service.CommentService;
import org.arjunaoverdrive.newsapp.web.dto.ErrorResponse;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentRequest;
import org.arjunaoverdrive.newsapp.web.dto.comment.CommentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comment v1", description = "Comments API v1.")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(
            summary = "Get comment by id.",
            description = "Get a comment by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentResponse> findCommentById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(commentMapper.toCommentResponse(commentService.findById(id)));
    }

    @Operation(
            summary = "Create comment.",
            description = "Create a new comment."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.toCommentResponse(commentService.createComment(commentMapper.toComment(request))));
    }

    @Operation(
            summary = "Update comment.",
            description = "Update an existing comment"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody @Valid CommentRequest request) {
        return ResponseEntity.accepted()
                .body(commentMapper.toCommentResponse(commentService.updateComment(id, commentMapper.toComment( request))));
    }

    @Operation(
            summary = "Delete comment.",
            description = "Delete a comment by id"
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
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
