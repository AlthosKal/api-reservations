package com.example.api_reservations.connector.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private String name;
    private String code;
    private String timeZone;
}
