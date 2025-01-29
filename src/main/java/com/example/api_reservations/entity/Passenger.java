package com.example.api_reservations.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// Entidad que representa a un pasajero
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName; // Nombre del pasajero
    private String lastName; // Apellido del pasajero
    private String documentNumber; // Número de documento de identidad
    private String documentType; // Tipo de documento (DNI, Pasaporte, etc.)
    private LocalDate birthDate; // Fecha de nacimiento

    // Relación ManyToOne con Reservation (Un pasajero pertenece a una reserva)
    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;
}
