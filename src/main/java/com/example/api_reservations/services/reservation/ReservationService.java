package com.example.api_reservations.services.reservation;

import com.example.api_reservations.dto.ReservationDTO;
import com.example.api_reservations.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interfaz que define las operaciones disponibles para el servicio de reservaciones.
 * Proporciona métodos para crear, leer, actualizar y eliminar reservaciones.
 */
public interface ReservationService {
    /**
     * Obtiene todas las reservaciones existentes en el sistema.
     * @return Lista de reservaciones convertidas a DTOs
     */
    List<ReservationDTO> getReservations();

    /**
     * Busca una reservación específica por su ID.
     * @param id Identificador único de la reservación
     * @return DTO de la reservación encontrada
     * @throws ResourceNotFoundException si no se encuentra la reservación
     */
    ReservationDTO getReservationById(Long id);

    /**
     * Valida los datos de una nueva reservación y la crea si es válida.
     * @param reservationDTO DTO con los datos de la reservación a validar y crear
     * @return DTO de la reservación creada
     * @throws ResourceNotFoundException si la validación falla
     */
    ReservationDTO validateAndCreateReservation(ReservationDTO reservationDTO);

    /**
     * Crea una nueva reservación sin validación previa.
     * @param reservationDTO DTO con los datos de la reservación a crear
     * @return DTO de la reservación creada
     * @throws ResourceNotFoundException si ya existe una reservación con el ID proporcionado
     */
    ReservationDTO createReservation(ReservationDTO reservationDTO);

    /**
     * Actualiza una reservación existente.
     * @param id Identificador único de la reservación a actualizar
     * @param reservationDTO DTO con los nuevos datos de la reservación
     * @return DTO de la reservación actualizada
     * @throws ResourceNotFoundException si no se encuentra la reservación
     */
    ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO);

    /**
     * Elimina una reservación existente.
     * @param id Identificador único de la reservación a eliminar
     * @throws ResourceNotFoundException si no se encuentra la reservación
     */
    void deleteReservation(Long id);
}