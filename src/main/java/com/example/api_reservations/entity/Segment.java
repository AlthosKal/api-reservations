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

    @ManyToOne
    @JoinColumn(name = "itinerary_id", nullable = false) //Define la columna de la clave foranea
    private Itinerary itinerary;
}
