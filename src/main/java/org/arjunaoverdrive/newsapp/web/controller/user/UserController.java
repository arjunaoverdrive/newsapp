package org.arjunaoverdrive.newsapp.web.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.mapper.user.UserMapper;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.web.dto.user.UserListResponse;
import org.arjunaoverdrive.newsapp.web.dto.user.UserRequest;
import org.arjunaoverdrive.newsapp.web.dto.user.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(
                userMapper.toUserListResponse(userService.findAll(pageable))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                userMapper.userToResponse(userService.findUserById(id))
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userMapper.userToResponse(userService.create(userMapper.requestToUser(userRequest)))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                        userMapper.userToResponse(userService.update(userMapper.requestToUser(id, userRequest)))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
