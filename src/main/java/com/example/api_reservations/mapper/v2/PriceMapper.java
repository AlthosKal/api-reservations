package com.example.api_reservations.mapper.v2;

import com.example.api_reservations.dto.PriceDTO;
import com.example.api_reservations.entity.Price;
import org.mapstruct.Mapper;

// Mapper para convertir entre Price y PriceDTO
@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceDTO toDTO(Price price);
    Price toEntity(PriceDTO dto);
}