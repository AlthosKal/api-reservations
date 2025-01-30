package com.example.api_reservations.mapper.v2;

import com.example.api_reservations.dto.ItineraryDTO;
import com.example.api_reservations.entity.Itinerary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// Mapper para convertir entre Itinerary y ItineraryDTO
@Mapper(componentModel = "spring", uses = { PriceMapper.class, SegmentMapper.class })
public interface ItineraryMapper {

    @Mapping(source = "price", target = "price")
    @Mapping(source = "segments", target = "segments")
    ItineraryDTO toDTO(Itinerary itinerary);

    @Mapping(source = "price", target = "price")
    @Mapping(source = "segments", target = "segments")
    Itinerary toEntity(ItineraryDTO dto);

    // Métodos auxiliares para convertir listas
    List<ItineraryDTO> toDTOList(List<Itinerary> itineraries);

    List<Itinerary> toEntityList(List<ItineraryDTO> dtos);
}