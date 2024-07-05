package com.openclassrooms.controller;

import com.openclassrooms.dto.RentalListResponse;
import com.openclassrooms.dto.RentalRequest;
import com.openclassrooms.dto.RentalResponse;
import com.openclassrooms.entity.Rental;
import com.openclassrooms.entity.User;
import com.openclassrooms.service.FileService;
import com.openclassrooms.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rental", description = "Endpoints for managing rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private FileService fileService;

    /**
     * Récupère toutes les locations
     * @return liste de locations
     */
    @Operation(summary = "Get all rentals")
    @GetMapping
    public ResponseEntity<RentalListResponse> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        List<RentalResponse> rentalResponses = rentals.stream().map(this::convertToResponse).toList();
        RentalListResponse response = new RentalListResponse(rentalResponses);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupère une location par son ID
     * @param id ID de la location
     * @return location
     */
    @Operation(summary = "Get a rental by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToResponse(rentalService.getRentalById(id)));
    }

    /**
     * Crée une nouvelle location
     * @param name nom de la location
     * @param surface surface de la location
     * @param price prix de la location
     * @param picture image de la location
     * @param description description de la location
     * @return message de confirmation
     */
    @Operation(summary = "Create a new rental")
    @PostMapping
    public ResponseEntity<String> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Float surface,
            @RequestParam("price") Float price,
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("description") String description) throws IOException {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        RentalRequest request = new RentalRequest();
        request.setName(name);
        request.setSurface(surface);
        request.setPrice(price);
        request.setPicture(picture);
        request.setDescription(description);

        rentalService.createRental(request, user);

        return ResponseEntity.ok("{\"message\": \"Rental created!\"}");
    }

    /**
     * Met à jour une location par son ID
     * @param id ID de la location
     * @param name nom de la location
     * @param surface surface de la location
     * @param price prix de la location
     * @param picture image de la location
     * @param description description de la location
     * @return message de confirmation
     */
    @Operation(summary = "Update a rental by ID")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") Float surface,
            @RequestParam("price") Float price,
            @RequestParam(value = "picture", required = false) MultipartFile picture,
            @RequestParam("description") String description) throws IOException {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        RentalRequest request = new RentalRequest();
        request.setName(name);
        request.setSurface(surface);
        request.setPrice(price);
        request.setDescription(description);

        if (picture != null) {
            request.setPicture(picture);
        }

        rentalService.updateRental(id, request, user);

        return ResponseEntity.ok("{\"message\": \"Rental updated!\"}");
    }

    /**
     * Convertit un objet Rental en objet RentalResponse.
     * @param rental la location
     * @return location convertie en objet RentalResponse
     */
    private RentalResponse convertToResponse(Rental rental) {
        RentalResponse response = new RentalResponse();
        response.setId(rental.getId());
        response.setName(rental.getName());
        response.setSurface(rental.getSurface());
        response.setPrice(rental.getPrice());
        response.setPicture(rental.getPicture());
        response.setDescription(rental.getDescription());
        response.setOwner_id(rental.getOwner().getId());
        response.setCreated_at(rental.getCreated_at());
        response.setUpdated_at(rental.getUpdated_at());
        return response;
    }
}
