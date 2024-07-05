package com.openclassrooms.controller;

import com.openclassrooms.dto.UserResponse;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.UserRepository;
import com.openclassrooms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "User", description = "Endpoints for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Récupère un utilisateur par son ID
     * @param id ID de l'utilisateur
     * @return utilisateu
     */
    @Operation(summary = "Get a user by id")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
        return ResponseEntity.ok(userResponse);
    }

}
