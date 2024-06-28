package com.openclassrooms.controller;

import com.openclassrooms.dto.MessageRequest;
import com.openclassrooms.dto.MessageResponse;
import com.openclassrooms.entity.User;
import com.openclassrooms.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Message", description = "Endpoints for managing messages")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Send a message to a rental")
    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest) {
        log.info("Received message request: " + messageRequest);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
        messageRequest.setUser_id(user.getId());
        messageService.sendMessage(messageRequest);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Message send with success");
        return ResponseEntity.ok(messageResponse);
    }
}
