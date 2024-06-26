package com.openclassrooms.service;

import com.openclassrooms.DTO.MessageRequest;
import com.openclassrooms.entity.Message;
import com.openclassrooms.entity.Rental;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.MessageRepository;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendMessage(MessageRequest messageRequest) {
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
    }
}

