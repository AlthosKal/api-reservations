package com.example.api_reservations.services.reservation;

import com.example.api_reservations.dto.ReservationDTO;
import java.util.List;

public interface ReservationService {
    List<ReservationDTO> getReservations();
    ReservationDTO getReservationById(Long id);
    ReservationDTO validateAndCreateReservation(ReservationDTO reservationDTO);
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO);
    void deleteReservation(Long id);
}