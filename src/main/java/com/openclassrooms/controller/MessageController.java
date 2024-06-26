package com.openclassrooms.controller;

import com.openclassrooms.DTO.MessageRequest;
import com.openclassrooms.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Message", description = "Endpoints for managing messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Send a message to a rental")
    @PostMapping("/messages")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.sendMessage(messageRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Message send with success");
        return ResponseEntity.ok(response);
    }
}
