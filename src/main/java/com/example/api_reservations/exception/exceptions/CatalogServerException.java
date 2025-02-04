package com.example.api_reservations.exception.exceptions;

// Excepción para errores del servidor al interactuar con el catálogo
public class CatalogServerException extends RuntimeException {
    public CatalogServerException(String message) {
        super(message);
    }
}