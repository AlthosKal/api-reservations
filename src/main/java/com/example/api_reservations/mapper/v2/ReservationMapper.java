package com.example.api_reservations.mapper.v2;

import com.example.api_reservations.dto.ReservationDTO;
import com.example.api_reservations.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// Mapper para convertir entre Reservation y ReservationDTO
@Mapper(componentModel = "spring", uses = { PassengerMapper.class, ItineraryMapper.class })
public interface ReservationMapper {

    @Mapping(source = "passengers", target = "passengers")
    @Mapping(source = "itinerary", target = "itinerary")
    ReservationDTO toDTO(Reservation reservation);

    @Mapping(source = "passengers", target = "passengers")
    @Mapping(source = "itinerary", target = "itinerary")
    Reservation toEntity(ReservationDTO dto);

    // Métodos auxiliares para convertir listas
    List<ReservationDTO> toDTOList(List<Reservation> reservations);

    List<Reservation> toEntityList(List<ReservationDTO> dtos);
}