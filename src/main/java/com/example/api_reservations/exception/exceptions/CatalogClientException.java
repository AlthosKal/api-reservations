package com.example.api_reservations.exception.exceptions;

// Excepción para errores de cliente al interactuar con el catálogo
public class CatalogClientException extends RuntimeException {
    public CatalogClientException(String message) {
        super(message);
    }
}