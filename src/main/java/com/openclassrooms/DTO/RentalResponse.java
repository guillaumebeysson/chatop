package com.openclassrooms.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalResponse {
    private Long id;
    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
