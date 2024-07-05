package com.openclassrooms.dto;

import lombok.Data;

/**
 * Requête de message contenant les informations nécessaires pour envoyer un message.
 */
@Data
public class MessageRequest {
    private Long rental_id;
    private Long user_id;
    private String message;
}
