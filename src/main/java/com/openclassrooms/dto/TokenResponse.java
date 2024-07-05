package com.openclassrooms.dto;

import lombok.Data;

/**
 * Réponse contenant le token à renvoyer à l'utilisateur.
 */
@Data
public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
