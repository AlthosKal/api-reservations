package com.example.api_reservations.dto;

import com.example.api_reservations.validations.CityFormatConstraint;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentDTO {

    @CityFormatConstraint
    private String origin;

    @CityFormatConstraint
    private String destination;

    private String departureDate;

    private String arrivalDate;

    private String carrier;
}
