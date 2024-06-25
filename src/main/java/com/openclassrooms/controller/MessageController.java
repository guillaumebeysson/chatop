package com.openclassrooms.controller;

import com.openclassrooms.entity.Message;
import com.openclassrooms.entity.Rental;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.MessageRepository;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Tag(name = "Message", description = "Endpoints for managing messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Send a message to a rental")
    @PostMapping("/messages")
    public String sendMessage(@RequestBody MessageRequest messageRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail);

        Rental rental = rentalRepository
                .findById(Long.valueOf(messageRequest.getRentalId()))
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Message message = new Message();
        message.setUser(user);
        message.setRental(rental);
        message.setMessage(messageRequest.getMessage());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        messageRepository.save(message);

        return "{\"message\": \"Message send with success\"}";
    }

    // DTO class defined within the controller
    @Data
    public static class MessageRequest {
        // Getters and setters
        private Integer rentalId;
        private String message;

    }
}