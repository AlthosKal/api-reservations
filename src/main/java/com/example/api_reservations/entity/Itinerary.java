package com.example.api_reservations.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// Entidad que representa un itinerario de viaje
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itineraries")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación bidireccional con Segment (Un Itinerary puede tener múltiples Segmentos)
    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Segment> segments = new ArrayList<>();

    // Relación OneToOne con Price (Cada Itinerary tiene un solo Price asociado)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id", referencedColumnName = "id", nullable = false)
    private Price price;

    // Método auxiliar para agregar segmentos a la lista
    public void addSegment(Segment segment) {
        segment.setItinerary(this); // Establece la relación inversa
        this.segments.add(segment);
    }
}