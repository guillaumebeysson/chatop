package com.openclassrooms.controller;

import com.openclassrooms.dto.AuthRequest;
import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.dto.TokenResponse;
import com.openclassrooms.dto.UserResponse;
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

    /**
     * Authentifie un utilisateur et retourne un token JWT
     * @param authRequest requête d'authentification
     * @return token JWT
     */
    @Operation(summary = "Authenticate a user and return a JWT token")
    @PostMapping("/login")
    public TokenResponse login(@RequestBody AuthRequest authRequest) {
        return authService.authenticateUser(authRequest);
    }

    /**
     * Enregistre un nouvel utilisateur
     * @param registerRequest requête d'enregistrement
     * @return token JWT
     */
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public TokenResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    /**
     * Récupère l'utilisateur actuellement authentifié
     * @return utilisateur actuel
     */
    @Operation(summary = "Get the current authenticated user")
    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        return authService.getCurrentUser();
    }
}


