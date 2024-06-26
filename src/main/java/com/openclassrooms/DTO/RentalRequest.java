package com.openclassrooms.DTO;

import lombok.Data;

@Data
public class RentalRequest {
    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private Long ownerId;
}
