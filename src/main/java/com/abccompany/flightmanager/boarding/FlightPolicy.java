package com.abccompany.flightmanager.boarding;

import com.abccompany.flightmanager.flight.Flight;
import com.abccompany.flightmanager.passenger.Passenger;

public interface FlightPolicy {
    boolean addPassenger(Flight flight, Passenger passenger);
    boolean removePassenger(Flight flight, Passenger passenger);
}
