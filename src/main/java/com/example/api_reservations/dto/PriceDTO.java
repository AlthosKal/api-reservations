package com.example.api_reservations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.math.BigDecimal;

// DTO que representa el precio de una reserva
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {

    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be a positive value")
    @JsonProperty("total_price")
    private BigDecimal totalPrice; // Precio total

    @NotNull(message = "Total tax is required")
    @PositiveOrZero(message = "Total tax must be zero or a positive value")
    @JsonProperty("total_tax")
    private BigDecimal totalTax; // Impuesto total

    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be a positive value")
    @JsonProperty("base_price")
    private BigDecimal basePrice; // Precio base sin impuestos
}