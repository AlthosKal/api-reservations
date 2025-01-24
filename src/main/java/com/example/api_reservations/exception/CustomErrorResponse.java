package com.example.api_reservations.exception;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CustomErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;


}
