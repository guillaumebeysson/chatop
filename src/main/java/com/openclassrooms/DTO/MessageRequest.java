package com.openclassrooms.DTO;

import lombok.Data;

@Data
public class MessageRequest {
    private Integer rentalId;
    private String message;
}
