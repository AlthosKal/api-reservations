package com.example.api_reservations.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

// Entidad que representa el precio de una reserva
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice; // Precio total
    private BigDecimal totalTax; // Impuesto total
    private BigDecimal basePrice; // Precio base sin impuestos
}