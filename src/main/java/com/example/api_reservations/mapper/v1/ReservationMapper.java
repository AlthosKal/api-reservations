package com.example.api_reservations.mapper.v1;

/*
import com.example.api_reservations.dto.PassengerDTO;
import com.example.api_reservations.dto.ReservationDTO;
import com.example.api_reservations.entity.Passenger;
import com.example.api_reservations.entity.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    private final PassengerMapper passengerMapper;
    private final ItineraryMapper itineraryMapper;

    public ReservationMapper(PassengerMapper passengerMapper, ItineraryMapper itineraryMapper) {
        this.passengerMapper = passengerMapper;
        this.itineraryMapper = itineraryMapper;
    }

    public ReservationDTO toDTO(Reservation reservation) {

        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());

        // Map passengers list
        if (reservation.getPassengers() != null) {
            List<PassengerDTO> passengerDTOs = reservation.getPassengers().stream()
                    .map(passengerMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setPassengers(passengerDTOs);
        }

        // Map itinerary
        dto.setItinerary(itineraryMapper.toDTO(reservation.getItinerary()));

        return dto;
    }

    public Reservation toEntity(ReservationDTO dto) {

        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());

        // Map passengers list
        if (dto.getPassengers() != null) {
            List<Passenger> passengers = dto.getPassengers().stream()
                    .map(passengerMapper::toEntity)
                    .collect(Collectors.toList());
            reservation.setPassengers(passengers);
        }

        // Map itinerary
        reservation.setItinerary(itineraryMapper.toEntity(dto.getItinerary()));

        return reservation;
    }

    // Convenience method for mapping lists
    public List<ReservationDTO> toDTOList(List<Reservation> reservations) {

        return reservations.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Convenience method for mapping lists
    public List<Reservation> toEntityList(List<ReservationDTO> dtos) {

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}*/