package com.example.api_reservations.dto;

import com.example.api_reservations.validations.CityFormatConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentDTO {

    @CityFormatConstraint
    private String origin;

    @CityFormatConstraint
    private String destination;

    @JsonProperty("departure_date")
    private String departureDate;

    @JsonProperty("arrival_date")
    private String arrivalDate;

    private String carrier;
}
