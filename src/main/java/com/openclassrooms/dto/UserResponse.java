package com.openclassrooms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String email;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public UserResponse(String email, String name, LocalDateTime created_at, LocalDateTime updated_at) {
        this.email = email;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
