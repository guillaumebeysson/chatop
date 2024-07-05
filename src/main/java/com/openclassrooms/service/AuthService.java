package com.openclassrooms.service;

import com.openclassrooms.dto.AuthRequest;
import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.dto.TokenResponse;
import com.openclassrooms.dto.UserResponse;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authentifie un utilisateur et renvoie un token.
     * @param authRequest les informations d'authentification de l'utilisateur
     * @return le token d'authentification
     */
    public String authenticateUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
        return jwtService.generateToken(userDetails);
    }

    /**
     * Enregistre un utilisateur et le connecte.
     * @param registerRequest les informations d'inscription de l'utilisateur
     * @return le token d'authentification
     */
    public String registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(registerRequest.getEmail());
        authRequest.setPassword(registerRequest.getPassword());
        return authenticateUser(authRequest);
    }

    /**
     * Récupère l'utilisateur actuellement connecté.
     * @return les informations de l'utilisateur
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

}
