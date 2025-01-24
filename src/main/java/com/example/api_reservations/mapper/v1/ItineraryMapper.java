package com.example.api_reservations.mapper.v1;
/*
import com.example.api_reservations.dto.ItineraryDTO;
import com.example.api_reservations.dto.SegmentDTO;
import com.example.api_reservations.entity.Itinerary;
import com.example.api_reservations.entity.Segment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItineraryMapper {

    private final SegmentMapper segmentMapper;
    private final PriceMapper priceMapper;

    public ItineraryMapper(SegmentMapper segmentMapper, PriceMapper priceMapper) {
        this.segmentMapper = segmentMapper;
        this.priceMapper = priceMapper;
    }

    public ItineraryDTO toDTO(Itinerary itinerary) {
        if (itinerary == null) {
            return null;
        }

        ItineraryDTO dto = new ItineraryDTO();

        // Map segments list
        if (itinerary.getSegments() != null) {
            List<SegmentDTO> segmentDTOs = itinerary.getSegments().stream()
                    .map(segmentMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setSegments(segmentDTOs);
        }

        // Map price
        dto.setPrice(priceMapper.toDTO(itinerary.getPrice()));

        return dto;
    }

    public Itinerary toEntity(ItineraryDTO dto) {
        if (dto == null) {
            return null;
        }

        Itinerary itinerary = new Itinerary();

        // Map segments list
        if (dto.getSegments() != null) {
            List<Segment> segments = dto.getSegments().stream()
                    .map(segmentMapper::toEntity)
                    .collect(Collectors.toList());
            itinerary.setSegments(segments);
        }

        // Map price
        itinerary.setPrice(priceMapper.toEntity(dto.getPrice()));

        return itinerary;
    }

    // Convenience method for mapping lists
    public List<ItineraryDTO> toDTOList(List<Itinerary> itineraries) {
        if (itineraries == null) {
            return null;
        }

        return itineraries.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Convenience method for mapping lists
    public List<Itinerary> toEntityList(List<ItineraryDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

 */