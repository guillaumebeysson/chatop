package com.openclassrooms.controller;

import com.openclassrooms.entity.User;
import com.openclassrooms.repository.UserRepository;
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
    private UserRepository userRepository;

    /**
     * Récupère un utilisateur par son ID
     * @param id ID de l'utilisateur
     * @return utilisateur
     */
    @Operation(summary = "Get a user by id")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findById(id).get());
    }

}
