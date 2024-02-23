package org.arjunaoverdrive.newsapp.web.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.mapper.user.UserMapper;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.web.dto.ErrorResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserListResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserRequest;
import org.arjunaoverdrive.newsapp.web.dto.user.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@Tag(name = "User v1", description = "User API v1")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Get all users.",
            description = "Get a page of users."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserListResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping
    public ResponseEntity<UserListResponse> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(
                userMapper.toUserListResponse(userService.findAllUsers(pageable))
        );
    }


    @Operation(
            summary = "Get user by id.",
            description = "Get an existing user by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class),  mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),  mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                userMapper.userToResponse(userService.findUserById(id))
        );
    }

    @Operation(
            summary = "Create user.",
            description = "Create a new user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class),  mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),  mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userMapper.userToResponse(userService.createUser(userMapper.requestToUser(userRequest)))
                );
    }


    @Operation(
            summary = "Update user.",
            description = "Update an existing user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class),  mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),  mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),  mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                        userMapper.userToResponse(userService.updateUser(userMapper.requestToUser(id, userRequest)))
                );
    }

    @Operation(
            summary = "Delete user.",
            description = "Delete user by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204"
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class),  mediaType = "application/json")
                    }
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
