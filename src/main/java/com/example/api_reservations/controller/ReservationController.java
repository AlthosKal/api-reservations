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

@Validated
@RestController
@RequestMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReservationController implements ReservationResource {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        log.info("Getting all reservations");
        List<ReservationDTO> reservations = reservationService.getReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        log.info("Getting reservation with id {}", id);
        ReservationDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @RateLimiter(name = "post-reservation", fallbackMethod = "fallbackPost")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservation) {
        log.info("Creating a new reservation");
        ReservationDTO createdReservation = reservationService.validateAndCreateReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@Valid @PathVariable Long id,
            @RequestBody ReservationDTO reservation) {
        log.info("Updating reservation with id {}", id);
        ReservationDTO updatedReservation = reservationService.updateReservation(id, reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("Deleting reservation with id {}", id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // Fallback method for createReservation
    public ResponseEntity<ReservationDTO> fallbackPost(ReservationDTO reservation, Throwable throwable) {
        // Log the reason why the fallback was triggered
        String fallbackMessage = "Rate limit exceeded. Please try again later.";
        log.warn("Fallback triggered for createReservation. Reason: {}", throwable.getMessage());

        // Create a default response object for the reservation
        ReservationDTO fallbackReservation = new ReservationDTO();
        fallbackReservation.setId(null);

        // Create a default ItineraryDTO
        ItineraryDTO fallbackItinerary = new ItineraryDTO();
        fallbackItinerary.setSegments(Collections.emptyList()); // No segments available
        fallbackItinerary.setPrice(null); // No price available

        // Set the default itinerary in the fallback reservation
        fallbackReservation.setItinerary(fallbackItinerary);

        // No passengers available in the fallback response
        fallbackReservation.setPassengers(Collections.emptyList());

        // Return a TOO_MANY_REQUESTS status with the fallback response
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "3") // Optional: Suggest a
                                                                                              // retry after 3 seconds
                .body(fallbackReservation);
    }

}