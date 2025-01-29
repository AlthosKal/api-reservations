package com.example.api_reservations.controller;

import com.example.api_reservations.dto.ItineraryDTO;
import com.example.api_reservations.dto.ReservationDTO;
import com.example.api_reservations.services.reservation.ReservationService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

// Controlador REST para gestionar reservas
@Validated
@RestController
@RequestMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReservationController implements ReservationResource {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    // Obtiene todas las reservas disponibles
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        log.info("Getting all reservations");
        List<ReservationDTO> reservations = reservationService.getReservations();
        return ResponseEntity.ok(reservations);
    }

    // Obtiene una reserva específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        log.info("Getting reservation with id {}", id);
        return ResponseEntity.ok(reservation);
    }

    // Crea una nueva reserva con validación y limitador de tasa para evitar sobrecarga
    @PostMapping
    @RateLimiter(name = "post-reservation", fallbackMethod = "fallbackPost")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservation) {
        ReservationDTO createdReservation = reservationService.validateAndCreateReservation(reservation);
        log.info("Creating a new reservation");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    // Actualiza una reserva existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@Valid @PathVariable Long id,
                                                            @RequestBody ReservationDTO reservation) {
        ReservationDTO updatedReservation = reservationService.updateReservation(id, reservation);
        log.info("Updating reservation with id {}", id);
        return ResponseEntity.ok(updatedReservation);
    }

    // Elimina una reserva por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("Deleting reservation with id {}", id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // Método de fallback en caso de superar el límite de solicitudes para crear reservas
    public ResponseEntity<ReservationDTO> fallbackPost(ReservationDTO reservation, Throwable throwable) {
        // Log del motivo del fallback
        String fallbackMessage = "Rate limit exceeded. Please try again later.";
        log.warn("Fallback triggered for createReservation. Reason: {}", throwable.getMessage());

        // Crea un objeto de reserva de respuesta por defecto
        ReservationDTO fallbackReservation = new ReservationDTO();
        fallbackReservation.setId(null);

        // Crea un itinerario de reserva predeterminado vacío
        ItineraryDTO fallbackItinerary = new ItineraryDTO();
        fallbackItinerary.setSegments(Collections.emptyList()); // Sin segmentos disponibles
        fallbackItinerary.setPrice(null); // Sin precio disponible

        // Asigna el itinerario vacío a la reserva
        fallbackReservation.setItinerary(fallbackItinerary);

        // No hay pasajeros en la respuesta de fallback
        fallbackReservation.setPassengers(Collections.emptyList());

        // Retorna una respuesta con código HTTP 429 (Too Many Requests) y sugiere reintentar después de 3 segundos
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "3")
                .body(fallbackReservation);
    }
}
