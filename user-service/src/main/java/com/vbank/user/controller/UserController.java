package com.vbank.user.controller;

import com.vbank.user.dto.LoginRequest;
import com.vbank.user.dto.LoginResponse;
import com.vbank.user.dto.RegisterRequest;
import com.vbank.user.dto.RegisterResponse;
import com.vbank.user.dto.UserResponse;
import com.vbank.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(request));
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserResponse> getProfile(
            @PathVariable UUID userId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getProfile(userId));
    }
}
