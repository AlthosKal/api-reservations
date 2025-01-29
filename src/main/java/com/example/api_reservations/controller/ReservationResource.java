package com.example.api_reservations.controller;

import com.example.api_reservations.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "API Documentation", description = "Documentation for the API endpoints")
public interface ReservationResource {

    @Operation(description = "Get the information about reservations", responses = {
            @ApiResponse(responseCode = "200", description = "Return the information of all the reservations", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = """
                        {
                          "reservations": [
                            {
                              "id": 1,
                              "passengers": [
                                {
                                  "first_name": "Juan",
                                  "last_name": "Perez",
                                  "document_number": 123456,
                                  "document_type": "CC",
                                  "birth_date": "2007-01-24"
                                }
                              ],
                              "itinerary": {
                                "segments": [
                                  {
                                    "origin": "BUE",
                                    "destination": "BOG",
                                    "departure_date": "2025-02-01T10:00:00",
                                    "arrival_date": "2025-02-01T18:00:00",
                                    "carrier": "Avianca"
                                  }
                                ],
                                "price": {
                                  "total_price": 1500,
                                  "total_tax": 500,
                                  "base_price": 1000
                                }
                              }
                            }
                          ]
                        }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error while getting reservation information") })
    ResponseEntity<List<ReservationDTO>> getReservations();

    @Operation(description = "Get the information about one reservation by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Get reservation by Id successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "404", description = "The reservation does not exist", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = """
                        {
                          "message": "The reservation does not exist",
                          "status": 404
                        }
                    """))) }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Reservation ID") })
    ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id);

    @Operation(description = "Create one reservation", responses = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "400", description = "The data reservation is invalid", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "429", description = "Too many requests or a necessary service has failed in process. Please try again later.", headers = @Header(name = "Retry-After", schema = @Schema(type = "integer"), description = "Time in seconds until you can retry")),
            @ApiResponse(responseCode = "500", description = "Error while creating reservation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
                    @ExampleObject(name = "Valid reservation example", summary = "Example to create a reservation", value = """
                                {
                                  "id": null,
                                  "passengers": [
                                    {
                                      "first_name": "Juan",
                                      "last_name": "Perez",
                                      "document_number": 123456,
                                      "document_type": "CC",
                                      "birth_date": "2007-01-24"
                                    }
                                  ],
                                  "itinerary": {
                                    "segments": [
                                      {
                                        "origin": "BUE",
                                        "destination": "BOG",
                                        "departure_date": "2025-02-01T10:00:00",
                                        "arrival_date": "2025-02-01T18:00:00",
                                        "carrier": "Avianca"
                                      }
                                    ],
                                    "price": {
                                      "total_price": 1500,
                                      "total_tax": 500,
                                      "base_price": 1000
                                    }
                                  }
                                }
                            """) })))
    ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservation);

    @Operation(description = "Update one reservation by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Updated reservation successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "404", description = "The reservation does not exist", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = """
                        {
                          "message": "The reservation does not exist",
                          "status": 404
                        }
                    """))),
            @ApiResponse(responseCode = "429", description = "Too many requests. Please try again later.", headers = @Header(name = "Retry-After", schema = @Schema(type = "integer"), description = "Time in seconds until you can retry")),
            @ApiResponse(responseCode = "500", description = "Error while updating reservation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Reservation ID") })
    ResponseEntity<ReservationDTO> updateReservation(@Valid @PathVariable Long id,
            @RequestBody ReservationDTO reservation);

    @Operation(description = "Delete one reservation by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Reservation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "The reservation does not exist", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = """
                        {
                          "message": "The reservation does not exist",
                          "status": 404
                        }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error deleting the reservation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Reservation ID") })
    ResponseEntity<Void> deleteReservation(@PathVariable Long id);
}
