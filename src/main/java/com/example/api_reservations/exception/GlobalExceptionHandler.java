package com.example.api_reservations.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

// Manejador global de excepciones para toda la aplicación
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepciones generales no capturadas específicamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGlobalException(Exception ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}