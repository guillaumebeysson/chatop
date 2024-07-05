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
    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère la location par son id.
     * @param id l'id de la location
     * @return la location
     */
    public RentalResponse getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        return convertToResponse(rental);
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
