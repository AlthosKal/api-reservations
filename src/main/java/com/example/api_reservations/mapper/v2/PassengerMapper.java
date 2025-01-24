package com.example.api_reservations.mapper.v2;

import com.example.api_reservations.dto.PassengerDTO;
import com.example.api_reservations.entity.Itinerary;
import com.example.api_reservations.entity.Passenger;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface PassengerMapper {
    PassengerDTO toDTO(Passenger passenger);
    Passenger toEntity(PassengerDTO dto);
    // Add additional methods for mapping lists
    List<PassengerDTO> toDTOList(List<Itinerary> itineraries);
    List<Passenger> toEntityList(List<PassengerDTO> dtos);
}
