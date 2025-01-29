// DTO (Data Transfer Object) que representa la información de una ciudad
package com.example.api_reservations.connector.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private String name; // Nombre de la ciudad
    private String code; // Código identificador de la ciudad
    private String timeZone; // Zona horaria de la ciudad
}