package com.example.api_reservations.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin;
    private String destination;
    private String departureDate;
    private String arrivalDate;
    private String carrier;

    // Relación ManyToOne con Itinerary
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id", nullable = false) // Define la columna de la clave foránea
    private Itinerary itinerary;
}
