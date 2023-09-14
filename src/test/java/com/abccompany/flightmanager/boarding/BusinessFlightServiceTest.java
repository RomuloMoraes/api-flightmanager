package com.abccompany.flightmanager.boarding;

import com.abccompany.flightmanager.flight.Flight;
import com.abccompany.flightmanager.flight.FlightCategory;
import com.abccompany.flightmanager.passenger.Passenger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BusinessFlightServiceTest {

    @Test
    @DisplayName("Given Economic Passenger When add to Business Flight Then Economic Passenger is not allowed to Economic Flight")
    void givenEcPassengerWhenAddEcPassengerToBsFlightThenEcPassengerIsNotAllowedToBsFlight() {

        //Given
        Flight flight = new Flight();
        FlightCategory flightCategory;
        BusinessFlightService bfs = new BusinessFlightService();

        Passenger passenger = new Passenger();
        passenger.setId("EC1");
        passenger.setName("Fulano");
        passenger.setVip(false);

        Set<Passenger> testListPassengers = new HashSet<>();
        flight.setCategory(FlightCategory.BUSINESS);
        flight.setId("FB1");
        flight.setPassengers(testListPassengers);

        //When
        boolean ans = bfs.addPassenger(flight, passenger);

        //Then
        assertFalse(ans);


    }

    @Test
    @DisplayName("Given VIP Passenger When add to Business Flight Then VIP Passenger is add to Business Flight")
    void givenAddPassengerVIPWhenAddPassengerVIPToFlightECThenPassengerVIPIsAddToFlightEC() {

        //Given
        Flight flight = new Flight();
        Passenger passenger = new Passenger();
        FlightCategory flightCategory;
        BusinessFlightService bfs = new BusinessFlightService();

        passenger.setId("VP2");
        passenger.setName("Ciclano");
        passenger.setVip(true);

        Set<Passenger> testListPassengers = new HashSet<>();
        flight.setCategory(FlightCategory.BUSINESS);
        flight.setId("BF1");
        flight.setPassengers(testListPassengers);

        //When
        bfs.addPassenger(flight, passenger);

        //Then
        Passenger passengerTest = testListPassengers.stream()
                .filter(passenger1 -> "VP2".equals(passenger1.getId()))
                .findAny()
                .orElse(null);

        String passengerTestID = passengerTest.getId();

        assertEquals("VP2", passengerTestID);

    }

    @Test
    @DisplayName("Given Economic Passenger When Remove from Economic Flight Then Economic Passenger is removed from Economic Flight")
    void givenRemovePassengerEWhenRemovePassengerEFromFlightECThenPassengerIsRemovedToFlightEC() {

        //Given
        EconomicFlightService efs = new EconomicFlightService();

        Passenger passenger = new Passenger();
        passenger.setId("P8");
        passenger.setName("Fulano");
        passenger.setVip(false);
        Set<Passenger> testListPassengers = new HashSet<>();

        Flight flight = new Flight();
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