package com.example.api_reservations.repository;

import com.example.api_reservations.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para gestionar la persistencia de Passenger
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}