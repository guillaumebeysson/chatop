package com.openclassrooms.service;

import com.openclassrooms.DTO.RentalRequest;
import com.openclassrooms.entity.Rental;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    public void createRental(RentalRequest request) {
        User owner = userRepository.findById(request.getOwnerId()).orElseThrow(() -> new RuntimeException("Owner not found"));
        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        rental.setOwner(owner);
        rentalRepository.save(rental);
    }

    public void updateRental(Long id, RentalRequest request) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        rentalRepository.save(rental);
    }
}
