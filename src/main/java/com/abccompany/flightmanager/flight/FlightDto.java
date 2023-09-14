package com.abccompany.flightmanager.flight;

import com.abccompany.flightmanager.passenger.PassengerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class FlightDto {
    String id;
    FlightCategory category;
    List<PassengerDto> passengers;

    public static FlightDto from(Flight flight) {
        FlightDto dto = new FlightDto();
        dto.id = flight.getId();
        dto.category = flight.getCategory();
        dto.passengers = Collections.emptyList();
        return dto;
    }

    public static FlightDto withPassengers(Flight flight) {
        FlightDto dto = FlightDto.from(flight);
        dto.passengers = flight.getPassengers().stream().map(PassengerDto::from).collect(Collectors.toList());
        return dto;
    }

    public Flight toFlight() {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setCategory(category);
        flight.setPassengers(passengers.stream().map(PassengerDto::toPassenger).collect(Collectors.toSet()));
        return flight;
    }
}
