package com.example.api_reservations.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    @Valid
    @NotEmpty(message = "You must have at least one passenger")
    private List<PassengerDTO> passengers;

    @Valid
    @NotNull(message = "Itinerary is required")
    private ItineraryDTO itinerary;
}
