package com.openclassrooms.dto;

import lombok.Data;

/**
 * Requête d'authentification contenant les informations nécessaires pour authentifier un utilisateur.
 */
@Data
public class AuthRequest {
    private String email;
    private String password;
}

