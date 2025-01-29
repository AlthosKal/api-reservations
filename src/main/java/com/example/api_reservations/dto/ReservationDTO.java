package com.example.api_reservations.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

// DTO que representa una reserva completa
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id; // Identificador de la reserva

    @Valid
    @NotEmpty(message = "You must have at least one passenger")
    private List<PassengerDTO> passengers; // Lista de pasajeros asociados a la reserva

    @Valid
    @NotNull(message = "Itinerary is required")
    private ItineraryDTO itinerary; // Itinerario de la reserva
}