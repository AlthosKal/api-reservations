package com.example.api_reservations.repository;

import com.example.api_reservations.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para gestionar la persistencia de Price
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
}
