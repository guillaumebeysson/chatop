package com.openclassrooms.dto;

import lombok.Data;

import java.util.List;

@Data
public class RentalListResponse {
    private List<RentalResponse> rentals;

    public RentalListResponse(List<RentalResponse> rentals) {
        this.rentals = rentals;
    }
}
