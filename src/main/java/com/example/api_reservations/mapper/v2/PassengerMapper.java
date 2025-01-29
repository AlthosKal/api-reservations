package com.example.api_reservations.mapper.v2;

import com.example.api_reservations.dto.PassengerDTO;
import com.example.api_reservations.entity.Passenger;
import org.mapstruct.Mapper;

import java.util.List;

// Mapper para convertir entre Passenger y PassengerDTO
@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDTO toDTO(Passenger passenger);
    Passenger toEntity(PassengerDTO dto);

    // Métodos auxiliares para convertir listas
    List<PassengerDTO> toDTOList(List<Passenger> passengers);
    List<Passenger> toEntityList(List<PassengerDTO> dtos);
}