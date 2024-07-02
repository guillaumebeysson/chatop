package com.openclassrooms.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Réponse contenant les informations d'une location.
 */
@Data
public class RentalResponse {
    private Long id;
    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private Long owner_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}