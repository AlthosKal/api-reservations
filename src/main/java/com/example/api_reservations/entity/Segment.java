package com.example.api_reservations.entity;

import jakarta.persistence.*;
import lombok.*;

// Entidad que representa un segmento de un itinerario
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin; // Ciudad de origen del segmento
    private String destination; // Ciudad de destino del segmento
    private String departureDate; // Fecha de salida del segmento
    private String arrivalDate; // Fecha de llegada del segmento
    private String carrier; // Transportista o aerolínea del segmento

    // Relación ManyToOne con Itinerary (Un segmento pertenece a un itinerario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id", nullable = false) // Define la columna de la clave foránea
    private Itinerary itinerary;
}
