package com.example.api_reservations.mapper.v1;
/*
 * import com.example.api_reservations.dto.PassengerDTO; import com.example.api_reservations.entity.Passenger; import
 * org.springframework.stereotype.Component;
 *
 * import java.util.List; import java.util.stream.Collectors;
 *
 * @Component public class PassengerMapper {
 *
 * public PassengerDTO toDTO(Passenger passenger) { if (passenger == null) { return null; }
 *
 * PassengerDTO dto = new PassengerDTO(); dto.setFirstName(passenger.getFirstName());
 * dto.setLastName(passenger.getLastName()); dto.setDocumentNumber(passenger.getDocumentNumber());
 * dto.setDocumentType(passenger.getDocumentType()); dto.setBirthDate(passenger.getBirthDate());
 *
 * return dto; }
 *
 * public Passenger toEntity(PassengerDTO dto) { if (dto == null) { return null; }
 *
 * Passenger passenger = new Passenger(); passenger.setFirstName(dto.getFirstName());
 * passenger.setLastName(dto.getLastName()); passenger.setDocumentNumber(dto.getDocumentNumber());
 * passenger.setDocumentType(dto.getDocumentType()); passenger.setBirthDate(dto.getBirthDate());
 *
 * return passenger; }
 *
 * // Convenience method for mapping lists public List<PassengerDTO> toDTOList(List<Passenger> passengers) { if
 * (passengers == null) { return null; }
 *
 * return passengers.stream() .map(this::toDTO) .collect(Collectors.toList()); }
 *
 * // Convenience method for mapping lists public List<Passenger> toEntityList(List<PassengerDTO> dtos) { if (dtos ==
 * null) { return null; }
 *
 * return dtos.stream() .map(this::toEntity) .collect(Collectors.toList()); } }
 *
 */