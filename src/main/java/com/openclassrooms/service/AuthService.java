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

    public TokenResponse authenticateUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        return new TokenResponse(token);
    }

    public TokenResponse registerUser(RegisterRequest registerRequest) {
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

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }

}
