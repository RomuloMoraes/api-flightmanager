package com.abccompany.flightmanager.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("passenger")
public class PassengerController {
    private final PassengerRepository repository;

    @GetMapping
    public ResponseEntity<List<PassengerDto>> getAll() {
        return ResponseEntity.ok(repository.findAll().stream().map(PassengerDto::from).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<PassengerDto> create(@RequestBody PassengerDto passenger) {

        Optional<Passenger> existing = repository.findByName(passenger.getName());

        if (existing.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT).build();
        }

        Passenger saved = repository.save(passenger.toPassenger());

        return ResponseEntity.status(HttpStatus.CREATED).body(PassengerDto.from(saved));
    }

    @GetMapping("{id}")
    public ResponseEntity<PassengerDto> retrieve(@PathVariable String id) {
        Optional<Passenger> existing = repository.findById(id);
        return existing
                .map((e) -> ResponseEntity.ok().body(PassengerDto.from(e)))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<PassengerDto> update(@RequestBody PassengerDto passenger) {
        Optional<Passenger> existing = repository.findByName(passenger.getName());
        if (!existing.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).build();
        }
        Passenger saved = repository.save(passenger.toPassenger());
        return ResponseEntity.status(HttpStatus.OK)
                .body(PassengerDto.from(saved));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PassengerDto> delete(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
