package com.example.api_reservations.mapper.v2;

import com.example.api_reservations.dto.SegmentDTO;
import com.example.api_reservations.entity.Segment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SegmentMapper {
    SegmentDTO toDTO(Segment segment);

    Segment toEntity(SegmentDTO segmentDTO);
}
