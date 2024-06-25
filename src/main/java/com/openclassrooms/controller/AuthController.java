package com.openclassrooms.controller;

import com.openclassrooms.DTO.AuthRequest;
import com.openclassrooms.DTO.RegisterRequest;
import com.openclassrooms.DTO.UserResponse;
import com.openclassrooms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Authenticate a user and return a JWT token")
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        return authService.authenticateUser(authRequest);
    }

    @Operation(summary = "Get login page")
    @GetMapping("/login")
    public String loginGet() {
        return "Login page";
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return "User registered successfully";
    }

    @Operation(summary = "Get register page")
    @GetMapping("/register")
    public String registerGet() {
        return "Register page";
    }

    @Operation(summary = "Get the current authenticated user")
    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        return authService.getCurrentUser();
    }
}


