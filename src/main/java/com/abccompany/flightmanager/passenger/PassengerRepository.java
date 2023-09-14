package com.abccompany.flightmanager.passenger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, String> {
    Optional<Passenger> findByName(String name);
}
