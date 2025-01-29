package com.example.api_reservations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

// DTO que representa un pasajero
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    @NotBlank(message = "firstName is required")
    @JsonProperty("first_name")
    private String firstName; // Nombre del pasajero

    @NotBlank(message = "lastName is required")
    @JsonProperty("last_name")
    private String lastName; // Apellido del pasajero

    @NotNull(message = "documentNumber is required")
    @Positive(message = "documentNumber must be a positive number")
    @Digits(integer = 10, fraction = 0, message = "documentNumber must be a 10-digit number")
    @JsonProperty("document_number")
    private Integer documentNumber; // Número de documento del pasajero

    @NotBlank(message = "documentType is required")
    @JsonProperty("document_type")
    private String documentType; // Tipo de documento del pasajero

    @Past(message = "Your birthDate needs to be a date in the past")
    @JsonProperty("birth_date")
    private LocalDate birthDate; // Fecha de nacimiento del pasajero
}