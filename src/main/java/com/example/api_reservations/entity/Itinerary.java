package com.example.api_reservations.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itineraries")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación bidireccional con Segment
    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Segment> segments = new ArrayList<>();

    // Relación OneToOne con Price
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id", referencedColumnName = "id", nullable = false)
    private Price price;

    // Método auxiliar para agregar segmentos
    public void addSegment(Segment segment) {
        segment.setItinerary(this); // Establece la relación inversa
        this.segments.add(segment);
    }
}
