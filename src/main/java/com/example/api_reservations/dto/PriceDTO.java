package com.example.api_reservations.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {
    @NotEmpty
    private BigDecimal totalPrice;

    @NotEmpty
    private BigDecimal totalTax;

    @NotEmpty
    private BigDecimal basePrice;
}
