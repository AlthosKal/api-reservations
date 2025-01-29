package com.example.api_reservations.repository;

import com.example.api_reservations.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para gestionar la persistencia de Reservation
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}