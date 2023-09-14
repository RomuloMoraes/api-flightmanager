package com.abccompany.flightmanager.boarding;

import com.abccompany.flightmanager.flight.Flight;
import com.abccompany.flightmanager.flight.FlightCategory;
import com.abccompany.flightmanager.flight.FlightRepository;
import com.abccompany.flightmanager.passenger.Passenger;
import com.abccompany.flightmanager.passenger.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("boarding")
public class BoardingController {
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    @PostMapping("{flightId}/{passengerId}")
    public ResponseEntity<?> addPassenger(@PathVariable String flightId, @PathVariable String passengerId) {
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        if (!flightOptional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }
        Optional<Passenger> passengerOptional = passengerRepository.findById(passengerId);
        if (!passengerOptional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }
        Flight flight = flightOptional.get();
        Boolean added = flightOptional
                .map(f -> FlightCategory.BUSINESS.equals(f.getCategory()) ? new BusinessFlightService() : new EconomicFlightService())
                .map(flightPolicy -> flightPolicy.addPassenger(flight, passengerOptional.get()))
                .orElse(false);
        if (added) {
            flightRepository.save(flight);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @DeleteMapping("{flightId}/{passengerId}")
    public ResponseEntity<?> removePassenger(@PathVariable String flightId, @PathVariable String passengerId) {
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        if (!flightOptional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }
        Optional<Passenger> passengerOptional = passengerRepository.findById(passengerId);
        if (!passengerOptional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }
        Flight flight = flightOptional.get();
        Boolean added = flightOptional
                .map(f -> FlightCategory.BUSINESS.equals(f.getCategory()) ? new BusinessFlightService() : new EconomicFlightService())
                .map(flightPolicy -> flightPolicy.removePassenger(flight, passengerOptional.get()))
                .orElse(false);
        if (added) {
            flightRepository.save(flight);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
