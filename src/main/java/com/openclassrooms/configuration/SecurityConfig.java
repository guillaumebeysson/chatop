package com.openclassrooms.configuration;

import com.openclassrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Liste des URI autorisées sans authentification
     */
    private static final String[] AUTH_WHITELIST = {
            "/api/auth/login",
            "/api/auth/register",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/images/**"
    };

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * Configuration de la sécurité
     * @param http La configuration HTTP
     * @return La configuration de la sécurité
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Encodage du mot de passe
     * @return mot de passe encodé
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cette méthode retourne une instance de UserDetailsService qui permet de charger les détails
       d'un utilisateur à partir de son adresse e-mail. Le service recherche l'utilisateur en utilisant
       le repository des utilisateurs (userRepository).
     * @return charge les détails de l'utilisateur par adresse e-mail
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username);
    }

    /**
     * Valider les informations d'authentification d'un utilisateur en utilisant le service
       UserDetailsService et l'encodage du mot de passe.
     * @return Un AuthenticationProvider configuré pour l'authentification des utilisateurs.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configuration de l'authentification
     * @param config La configuration de l'authentification
     * @return L'instance d'AuthenticationManager utilisée pour gérer l'authentification des utilisateurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
