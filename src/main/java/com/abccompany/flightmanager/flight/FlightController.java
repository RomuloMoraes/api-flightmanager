package com.abccompany.flightmanager.flight;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("flight")
public class FlightController {
    private final FlightRepository repository;
    @GetMapping
    public ResponseEntity<List<FlightDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(FlightDto::from).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<FlightDto> create(@RequestBody FlightDto flight) {

        Optional<Flight> existing = repository.findById(flight.getId());

        if (existing.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }

        Flight saved = repository.save(flight.toFlight());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FlightDto.from(saved));
    }

    @GetMapping("{id}")
    public ResponseEntity<FlightDto> retrieve(@PathVariable String id) {
        Optional<Flight> existing = repository.findById(id);
        return existing
                .map((e) -> ResponseEntity.ok().body(FlightDto.withPassengers(e)))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<FlightDto> update(@RequestBody FlightDto flight) {
        Optional<Flight> existing = repository.findById(flight.getId());
        if (!existing.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).build();
        }
        Flight saved = repository.save(flight.toFlight());
        return ResponseEntity.status(HttpStatus.OK)
                .body(FlightDto.from(saved));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
