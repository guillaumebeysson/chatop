package com.openclassrooms.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Requête de location contenant les informations nécessaires pour créer une location.
 */
@Data
public class RentalRequest {
    private String name;
    private Float surface;
    private Float price;
    private MultipartFile picture;
    private String description;
}
