package com.example.api_reservations.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDTO {

    @Valid
    @NotEmpty(message = "You must have at least one segment ")
    private List<SegmentDTO> segments;

    @Valid
    @NotEmpty(message = "Price is required")
    private PriceDTO price;
}
