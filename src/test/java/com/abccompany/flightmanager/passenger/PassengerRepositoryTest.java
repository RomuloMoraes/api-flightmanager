package com.abccompany.flightmanager.passenger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PassengerRepositoryTest {

    @Autowired
    PassengerRepository passengerRepository;

    Passenger passenger;

    @Test
    void shouldFindPassengerByName() {
        // recuperar o nome
        passenger = passengerRepository.findByName("Jonh Smith").get();
        // preciso fazer o assert
        assertEquals("Jonh Smith", passenger.getName());
    }

    @Test
    void shouldFindPassengerByName2() {
        assertEquals("Jonh Smith", passengerRepository.findByName("Jonh Smith").get().getName());
    }
}