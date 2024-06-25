package com.openclassrooms.DTO;

import lombok.Data;

@Data
public class UserResponse {
    private String email;
    private String name;

    public UserResponse(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
