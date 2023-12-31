package com.abccompany.flightmanager.boarding;

import com.abccompany.flightmanager.flight.Flight;
import com.abccompany.flightmanager.passenger.Passenger;

public class BusinessFlightService implements FlightPolicy {
    public boolean addPassenger(Flight flight, Passenger passenger) {
        if (!passenger.isVip()) {
            return false;
        }
        return flight.getPassengers().add(passenger);
    }

    public boolean removePassenger(Flight flight, Passenger passenger) {

        return false;
    }
}
