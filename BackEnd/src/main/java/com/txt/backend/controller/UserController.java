package com.txt.backend.controller;

import com.txt.backend.dto.UserRequest;
import com.txt.backend.dto.UserResponse;
import com.txt.backend.exception.ResourceNotFoundException;
import com.txt.backend.mapper.UserMapper;
import com.txt.backend.model.User;
import com.txt.backend.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        User created = userService.createUser(UserMapper.toEntity(request));
        return ResponseEntity.ok(UserMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers().stream()
                .map(UserMapper::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        User updated = userService.updateUser(id, UserMapper.toEntity(request));
        return ResponseEntity.ok(UserMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/quarantine")
    public ResponseEntity<Boolean> isUserInQuarantine(@PathVariable Long id) {
        return ResponseEntity.ok(userService.isUserInQuarantine(id));
    }
}
