package com.openclassrooms.controller;

import com.openclassrooms.DTO.RentalRequest;
import com.openclassrooms.DTO.RentalResponse;
import com.openclassrooms.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rental", description = "Endpoints for managing rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Get all rentals")
    @GetMapping
    public List<RentalResponse> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @Operation(summary = "Get a rental by ID")
    @GetMapping("/{id}")
    public RentalResponse getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }

    @Operation(summary = "Create a new rental")
    @PostMapping
    public ResponseEntity<Map<String, String>> createRental(@RequestBody RentalRequest request) {
        rentalService.createRental(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created!");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a rental by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(@PathVariable Long id, @RequestBody RentalRequest request) {
        rentalService.updateRental(id, request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated!");
        return ResponseEntity.ok(response);
    }
}

