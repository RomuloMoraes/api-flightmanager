package com.abccompany.flightmanager.boarding;

import com.abccompany.flightmanager.flight.Flight;
import com.abccompany.flightmanager.flight.FlightCategory;
import com.abccompany.flightmanager.passenger.Passenger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;


class EconomicFlightServiceTest {

    @Test
    @DisplayName("Given Economic Passenger When add to Economic Flight Then Economic Passenger is add to Economic Flight")
    void givenEcPassengerWhenAddEcPassengerToEcFlightThenEcPassengerIsAddToEcFlight() {

        //Given
        Flight flight = new Flight();
        Passenger passenger = new Passenger();
        FlightCategory flightCategory;
        EconomicFlightService efs = new EconomicFlightService();

        passenger.setId("P1");
        passenger.setName("Fulano");
        passenger.setVip(false);

        Set<Passenger> testListPassengers = new HashSet<>();
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setId("F1");
        flight.setPassengers(testListPassengers);

        //When
        efs.addPassenger(flight, passenger);

        //Then
        Passenger passengerTest = testListPassengers.stream()
                .filter(passenger1 -> "P1".equals(passenger1.getId()))
                .findAny()
                .get();
                //.orElse(null);

        String passengerTestID = passengerTest.getId();

        assertEquals("P1", passengerTestID);

    }

    @Test
    @DisplayName("Given VIP Passenger When add to Economic Flight Then VIP Passenger is add to flight")
    void givenAddPassengerVIPWhenAddPassengerVIPToFlightECThenPassengerVIPIsAddToFlightEC() {

        //Given
        Flight flight = new Flight();
        Passenger passenger = new Passenger();
        FlightCategory flightCategory;
        EconomicFlightService efs = new EconomicFlightService();

        passenger.setId("P2");
        passenger.setName("Ciclano");
        passenger.setVip(true);

        Set<Passenger> testListPassengers = new HashSet<>();
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setId("F1");
        flight.setPassengers(testListPassengers);

        //When
        efs.addPassenger(flight, passenger);

        //Then
        Passenger passengerTest = testListPassengers.stream()
                .filter(passenger1 -> "P2".equals(passenger1.getId()))
                .findAny()
                .orElse(null);

        String passengerTestID = passengerTest.getId();

        assertEquals("P2", passengerTestID);

    }

    @Test
    @DisplayName("Given Economic Passenger When Remove from Economic Flight Then Economic Passenger is removed from Economic Flight")
    void givenRemovePassengerEWhenRemovePassengerEFromFlightECThenPassengerIsRemovedToFlightEC() {

        //Given
        Flight flight = new Flight();
        Passenger passenger = new Passenger();
        FlightCategory flightCategory;
        EconomicFlightService efs = new EconomicFlightService();

        passenger.setId("P8");
        passenger.setName("Fulano");
        passenger.setVip(false);

        Set<Passenger> testListPassengers = new HashSet<>();
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setId("F1");
        flight.setPassengers(testListPassengers);

        efs.addPassenger(flight, passenger);

        //When
        efs.removePassenger(flight, passenger);

        //Then
        assertNull(testListPassengers.stream()
                .filter(passenger1 -> "P8".equals(passenger1.getId()))
                .findAny()
                .orElse(null));


    }

    @Test
    @DisplayName("Given VIP Passenger When Remove from Economic Flight Then VIP Passenger cannot be removed from Economic Flight")
    void givenRemovePassengerVIPWhenRemovePassengerVIPFromEcFlightThenPassengerVIPIsNotRemovedFlightEC() {

        //Given
        Flight flight = new Flight();
        Passenger passenger = new Passenger();
        FlightCategory flightCategory;
        EconomicFlightService efs = new EconomicFlightService();

        passenger.setId("P9");
        passenger.setName("Fulano");
        passenger.setVip(true);

        Set<Passenger> testListPassengers = new HashSet<>();
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setId("F1");
        flight.setPassengers(testListPassengers);

        efs.addPassenger(flight, passenger);

        //When
        boolean ans = efs.removePassenger(flight, passenger);
        //Then
        //assertFalse(efs.removePassenger(flight, passenger));
        assertFalse(ans);


    }

}