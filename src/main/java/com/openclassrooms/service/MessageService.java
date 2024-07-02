package com.openclassrooms.service;

import com.openclassrooms.dto.MessageRequest;
import com.openclassrooms.entity.Message;
import com.openclassrooms.entity.Rental;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.MessageRepository;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Envoie un message pour une location.
     * @param messageRequest les informations du message
     */
    public void sendMessage(MessageRequest messageRequest) {
        if (messageRequest.getUser_id() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        if (messageRequest.getRental_id() == null) {
            throw new IllegalArgumentException("Rental ID must not be null");
        }

        User user = userRepository.findById(messageRequest.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rental rental = rentalRepository
                .findById(messageRequest.getRental_id())
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
