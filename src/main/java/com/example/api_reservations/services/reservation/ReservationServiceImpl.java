package com.example.api_reservations.services.reservation;

import com.example.api_reservations.connector.CatalogConnector;
import com.example.api_reservations.connector.response.CityDTO;
import com.example.api_reservations.dto.ReservationDTO;
import com.example.api_reservations.dto.SegmentDTO;
import com.example.api_reservations.entity.*;
import com.example.api_reservations.exception.exceptions.CatalogClientException;
import com.example.api_reservations.exception.exceptions.CatalogServerException;
import com.example.api_reservations.exception.exceptions.ConcurrencyException;
import com.example.api_reservations.exception.exceptions.ResourceNotFoundException;
import com.example.api_reservations.mapper.v2.ReservationMapper;
import com.example.api_reservations.repository.ItineraryRepository;
import com.example.api_reservations.repository.PriceRepository;
import com.example.api_reservations.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de reservaciones que maneja la lógica de negocio para la gestión de reservaciones,
 * itinerarios, precios y pasajeros.
 */
@Service
@Transactional
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    // Inyección de dependencias mediante constructor
    private final ReservationRepository reservationRepository;
    private final ItineraryRepository itineraryRepository;
    private final PriceRepository priceRepository;
    private final ReservationMapper reservationMapper;
    private final CatalogConnector catalogConnector;

    /**
     * Constructor que inicializa todas las dependencias necesarias.
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository, ItineraryRepository itineraryRepository,
            PriceRepository priceRepository, ReservationMapper reservationMapper, CatalogConnector catalogConnector) {
        this.reservationRepository = reservationRepository;
        this.itineraryRepository = itineraryRepository;
        this.priceRepository = priceRepository;
        this.reservationMapper = reservationMapper;
        this.catalogConnector = catalogConnector;
    }

    /**
     * {@inheritDoc} Implementa consulta de solo lectura para obtener todas las reservaciones.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservationMapper.toDTOList(reservations);
    }

    /**
     * {@inheritDoc} Implementa consulta de solo lectura para obtener una reservación por ID.
     */
    @Override
    @Transactional(readOnly = true)
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return reservationMapper.toDTO(reservation);
    }

    /**
     * {@inheritDoc} Valida las ciudades de origen y destino de cada segmento antes de crear la reservación.
     */
    @Override
    public ReservationDTO validateAndCreateReservation(ReservationDTO reservationDTO) {
        // Validación de cada segmento del itinerario
        for (SegmentDTO segmentDTO : reservationDTO.getItinerary().getSegments()) {
            try {
                // Validar las ciudades de origen y destino
                validateCity(segmentDTO.getOrigin(), "Origin");
                validateCity(segmentDTO.getDestination(), "Destination");
            } catch (CatalogClientException | CatalogServerException ex) {
                log.error("Error during city validation: {}", ex.getMessage());
                throw new ResourceNotFoundException("City validation failed for segment: " + segmentDTO + " " + ex);
            }
        }

        // Crear la reserva después de la validación
        return createReservation(reservationDTO);
    }

    /**
     * Valida la existencia de una ciudad en el catálogo.
     *
     * @param cityCode
     *            Código de la ciudad a validar
     * @param cityType
     *            Tipo de ciudad (Origin/Destination)
     *
     * @throws ResourceNotFoundException
     *             si la ciudad no existe
     * @throws CatalogClientException
     *             si hay un error de cliente al consultar el catálogo
     * @throws CatalogServerException
     *             si hay un error de servidor al consultar el catálogo
     */
    private void validateCity(String cityCode, String cityType) {
        CityDTO city = catalogConnector.getCityDTOBlocking(cityCode);
        if (city == null) {
            throw new ResourceNotFoundException("Invalid city code for " + cityType + ": " + cityCode);
        }
    }

    /**
     * {@inheritDoc} Implementa la lógica de creación de una nueva reservación manejando las relaciones entre entidades
     * y su persistencia en cascada.
     */

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {

        // Verificar si existe la reserva
        if (reservationDTO.getId() != null) {
            // SI el ID no es nulo, verificar si ya existe en la base de datos
            boolean exists = reservationRepository.existsById(reservationDTO.getId());
            if (exists) {
                throw new ResourceNotFoundException("Reservation already exists with id: " + reservationDTO.getId());
            }
        }

        // Convertir DTO a entidad
        Reservation reservation = reservationMapper.toEntity(reservationDTO);

        // Guardar el Price primero (si no está siendo guardado automáticamente por cascade)
        Price price = reservation.getItinerary().getPrice();
        price = priceRepository.save(price); // Asegúrate de tener un PriceRepository

        // Guardar el Itinerary y sus Segment
        Itinerary itinerary = reservation.getItinerary();
        itinerary.setPrice(price); // Asignar el Price guardado al Itinerary

        // Asignar el Itinerary a cada Segment
        for (Segment segment : itinerary.getSegments()) {
            segment.setItinerary(itinerary); // Establece la relación inversa
        }

        // Guardar el Itinerary (esto también guardará los Segment debido a CascadeType.ALL)
        itinerary = itineraryRepository.save(itinerary);

        // Asignar el Itinerary guardado a la Reservation
        reservation.setItinerary(itinerary);

        // Asignar la Reservation a cada Passenger
        for (Passenger passenger : reservation.getPassengers()) {
            passenger.setReservation(reservation); // Establece la relación inversa
        }

        // Guardar la Reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Convertir la entidad guardada a DTO y devolverla
        return reservationMapper.toDTO(savedReservation);
    }

    /**
     * {@inheritDoc} Implementa la actualización de una reservación existente, manejando la actualización de todas las
     * entidades relacionadas y sus relaciones.
     */
    @Transactional
    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        try {// Cargar la reserva existente
            Reservation existingReservation = reservationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

            // Actualizar el Price
            Price updatedPrice = reservationMapper.toEntity(reservationDTO).getItinerary().getPrice();
            updatedPrice = priceRepository.save(updatedPrice);

            // Actualizar el Itinerary
            Itinerary updatedItinerary = reservationMapper.toEntity(reservationDTO).getItinerary();
            updatedItinerary.setPrice(updatedPrice);

            // Establecer la relación inversa entre los segmentos y el itinerario
            for (Segment segment : updatedItinerary.getSegments()) {
                segment.setItinerary(updatedItinerary);
            }
            updatedItinerary = itineraryRepository.save(updatedItinerary);

            // Limpiar los pasajeros existentes
            existingReservation.getPassengers().clear();
            reservationRepository.flush(); // Forzar la sincronización con la base de datos para eliminar los pasajeros
            // antiguos

            // Asignar los nuevos pasajeros a la reserva existente
            for (Passenger passenger : reservationMapper.toEntity(reservationDTO).getPassengers()) {
                passenger.setReservation(existingReservation); // Establecer la relación inversa
                existingReservation.getPassengers().add(passenger);
            }

            // Actualizar los demás campos de la reserva
            existingReservation.setItinerary(updatedItinerary);
            existingReservation.setPassengers(existingReservation.getPassengers());

            // Guardar la reserva actualizada
            Reservation savedReservation = reservationRepository.save(existingReservation);

            // Convertir la entidad guardada a DTO y devolverla
            return reservationMapper.toDTO(savedReservation);
        } catch (OptimisticLockingFailureException e) {
            throw new ConcurrencyException("The reservation was modified by another transtacion. Please retry");
        }
    }

    /**
     * {@inheritDoc} Implementa la eliminación de una reservación verificando su existencia previa.
     */
    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }
}