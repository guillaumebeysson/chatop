package com.openclassrooms.service;

import com.openclassrooms.DTO.RentalRequest;
import com.openclassrooms.DTO.RentalResponse;
import com.openclassrooms.entity.Rental;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public RentalResponse getRentalById(Long id) {
        return rentalRepository.findById(id).map(this::convertToResponse).orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    public RentalResponse createRental(RentalRequest request) {
        User owner = userRepository.findById(request.getOwnerId()).orElseThrow(() -> new RuntimeException("Owner not found"));
        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        rental.setOwner(owner);
        rental = rentalRepository.save(rental);
        return convertToResponse(rental);
    }

    public RentalResponse updateRental(Long id, RentalRequest request) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        rental = rentalRepository.save(rental);
        return convertToResponse(rental);
    }

    private RentalResponse convertToResponse(Rental rental) {
        RentalResponse response = new RentalResponse();
        response.setId(rental.getId());
        response.setName(rental.getName());
        response.setSurface(rental.getSurface());
        response.setPrice(rental.getPrice());
        response.setPicture(rental.getPicture());
        response.setDescription(rental.getDescription());
        response.setOwnerId(rental.getOwner().getId());
        response.setCreatedAt(rental.getCreatedAt());
        response.setUpdatedAt(rental.getUpdatedAt());
        return response;
    }
}

