package com.example.api_reservations.repository;

import com.example.api_reservations.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para gestionar la persistencia de Itinerary
@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
}