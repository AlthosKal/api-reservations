package com.example.api_reservations.services.reservation;

import com.example.api_reservations.connector.CatalogConnector;
import com.example.api_reservations.connector.response.CityDTO;
import com.example.api_reservations.dto.ReservationDTO;
import com.example.api_reservations.dto.SegmentDTO;
import com.example.api_reservations.entity.Reservation;
import com.example.api_reservations.exception.ResourceNotFoundException;
import com.example.api_reservations.mapper.v2.ReservationMapper;
import com.example.api_reservations.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CatalogConnector catalogConnector;

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservationMapper.toDTOList(reservations);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return reservationMapper.toDTO(reservation);
    }

    @Override
    public ReservationDTO validateAndCreateReservation(ReservationDTO reservationDTO) {
        // Iterar sobre cada segmento del itinerario
        for (SegmentDTO segmentDTO : reservationDTO.getItinerary().getSegments()) {
            // Validar la ciudad de origen con el CatalogConnector
            CityDTO originCity = catalogConnector.getCityDTOBlocking(segmentDTO.getOrigin());
            // Validar la ciudad de destino con el CatalogConnector
            CityDTO destinationCity = catalogConnector.getCityDTOBlocking(segmentDTO.getDestination());

            // Verificar si las ciudades son válidas
            if (originCity == null) {
                throw new ResourceNotFoundException("Invalid city code for Origin: " + segmentDTO.getOrigin());
            }
            if (destinationCity == null) {
                throw new ResourceNotFoundException("Invalid city code for Destination: " + segmentDTO.getDestination());
            }
        }

        // Crear la reserva después de la validación
        return createReservation(reservationDTO);
    }


    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        // Verificar si existe la reserva
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation not found with id: " + id);
        }

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setId(id);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }
}