package com.openclassrooms.controller;

import com.openclassrooms.service.CustomUserDetailsService;
import com.openclassrooms.service.JwtService;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Authenticate a user and return a JWT token")
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
            return jwtService.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Operation(summary = "Get login page")
    @GetMapping("/login")
    public String loginGet() {
        return "Login page";
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @Operation(summary = "Get register page")
    @GetMapping("/register")
    public String registerGet() {
        return "Register page";
    }

    @Operation(summary = "Get the current authenticated user")
    @GetMapping("/me")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}

@Data
class AuthRequest {
    private String email;
    private String password;

    // Getters and setters generated by Lombok
}

@Data
class RegisterRequest {
    private String email;
    private String name;
    private String password;

    // Getters and setters generated by Lombok
}

