package com.example.api_reservations.repository;

import com.example.api_reservations.entity.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para gestionar la persistencia de Segment
@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
}