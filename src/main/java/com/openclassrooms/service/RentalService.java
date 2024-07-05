package com.openclassrooms.service;

import com.openclassrooms.dto.RentalRequest;
import com.openclassrooms.dto.RentalResponse;
import com.openclassrooms.entity.Rental;
import com.openclassrooms.entity.User;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    /**
     * Récupère toutes les locations.
     * @return la liste des locations
     */
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Récupère la location par son id.
     * @param id l'id de la location
     * @return la location
     */
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    /**
     * Crée une location.
     * @param request les informations de la location
     * @param owner l'utilisateur propriétaire de la location
     */
    public void createRental(RentalRequest request, User owner) throws IOException {
        String pictureUrl = fileService.saveFile(request.getPicture());

        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(pictureUrl);
        rental.setDescription(request.getDescription());
        rental.setOwner(owner);
        rentalRepository.save(rental);
    }

    /**
     * Met à jour une location.
     * @param id l'id de la location
     * @param request les informations de la location
     * @param owner l'utilisateur propriétaire de la location
     */
    public void updateRental(Long id, RentalRequest request, User owner) throws IOException {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            String pictureUrl = fileService.saveFile(request.getPicture());
            rental.setPicture(pictureUrl);
        }
        rental.setOwner(owner);
        rentalRepository.save(rental);
    }

}
