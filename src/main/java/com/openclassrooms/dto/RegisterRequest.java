package com.openclassrooms.dto;

import lombok.Data;

/**
 * Requête d'inscription contenant les informations nécessaires pour inscrire un utilisateur.
 */
@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
}
