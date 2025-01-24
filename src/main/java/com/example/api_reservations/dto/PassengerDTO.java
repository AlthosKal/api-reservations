package com.example.api_reservations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {
    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "documentNumber is required")
    @Size(min = 1)
    private int documentNumber;

    @NotBlank(message = "documentType is required")
    private String documentType;

    @Past(message = "Your birhtDay need to be a date in the past")
    private LocalDate birthDate;
}
