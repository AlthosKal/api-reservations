package com.example.api_reservations.dto;

import com.example.api_reservations.validations.CityFormatConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// DTO que representa un segmento de un itinerario
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentDTO {

    @CityFormatConstraint
    private String origin; // Ciudad de origen del segmento

    @CityFormatConstraint
    private String destination; // Ciudad de destino del segmento

    @JsonProperty("departure_date")
    private String departureDate; // Fecha de salida del segmento

    @JsonProperty("arrival_date")
    private String arrivalDate; // Fecha de llegada del segmento

    private String carrier; // Transportista o aerolínea del segmento
}
