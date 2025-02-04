package com.example.api_reservations.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// Entidad que representa una reserva
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version // Agrega control de versión para Optimistic Locking
    private Integer version;
    // Relación OneToMany con Passenger (Una reserva puede tener múltiples pasajeros)
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers = new ArrayList<>();

    // Relación OneToOne con Itinerary (Cada reserva tiene un solo itinerario)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Itinerary itinerary;

    // Método para agregar pasajeros a la lista
    public void addPassenger(Passenger passenger) {
        passenger.setReservation(this); // Establece la relación inversa
        this.passengers.add(passenger);
    }
}