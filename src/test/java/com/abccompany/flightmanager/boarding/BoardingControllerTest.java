package com.abccompany.flightmanager.boarding;

import com.abccompany.flightmanager.flight.Flight;
import com.abccompany.flightmanager.flight.FlightCategory;
import com.abccompany.flightmanager.flight.FlightRepository;
import com.abccompany.flightmanager.passenger.Passenger;
import com.abccompany.flightmanager.passenger.PassengerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardingController.class)
@ExtendWith(MockitoExtension.class)
class BoardingControllerTest {

    @Autowired
    BoardingController boardingController;
    @MockBean
    FlightRepository flightRepository;

    @MockBean
    PassengerRepository passengerRepository;

    @Autowired
    MockMvc mockMvc;

    //add
    @Test
    @DisplayName("Given Economic Passenger When add to Economic Flight Then is added")
    void givenEcPassengerWhenAddToEcFlightThenEcPassengerIsAdd() throws Exception {

        //Given
        Passenger p1 = new Passenger();
        p1.setId("P1");
        p1.setName("Fulano");
        p1.setVip(false);

        Flight f1 = new Flight();
        f1.setId("ECF1");
        f1.setCategory(FlightCategory.ECONOMIC);
        f1.setPassengers(new HashSet<>());
        //f1.getPassengers().add(p1);

        //When
        Mockito.when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        Mockito.when(passengerRepository.findById(p1.getId()))
                .then(invocationOnMock -> Optional.of(p1));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", f1.getId(), p1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Given VIP Passenger When add to Economic Flight Then is added")
    void givenVipPassengerWhenAddToEcFlightThenIsAdd() throws Exception {

        //Given
        Passenger p2 = new Passenger();
        p2.setId("VP2");
        p2.setName("Tony Stark");
        p2.setVip(true);

        Flight f1 = new Flight();
        f1.setId("ECF1");
        f1.setCategory(FlightCategory.ECONOMIC);
        f1.setPassengers(new HashSet<>());

        //When
        Mockito.when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        Mockito.when(passengerRepository.findById(p2.getId()))
                .then(invocationOnMock -> Optional.of(p2));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", f1.getId(), p2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Given Economic Passenger When add to VIP Flight Then is Not Acceptable")
    void givenEcPassengerWhenAddToVipFlightThenIsNotAcceptable() throws Exception {

        //Given
        Passenger p1 = new Passenger();
        p1.setId("P1");
        p1.setName("Fulano");
        p1.setVip(false);

        Flight f1 = new Flight();
        f1.setId("ECF1");
        f1.setCategory(FlightCategory.BUSINESS);
        f1.setPassengers(new HashSet<>());

        //When
        Mockito.when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        Mockito.when(passengerRepository.findById(p1.getId()))
                .then(invocationOnMock -> Optional.of(p1));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", f1.getId(), p1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @DisplayName("Given VIP Passenger When add to VIP Flight Then VIP Passenger is added")
    void givenVipPassengerWhenAddToVipFlightThenIsAdd() throws Exception {

        //Given
        Passenger p1 = new Passenger();
        p1.setId("P1");
        p1.setName("Fulano");
        p1.setVip(true);

        Flight f1 = new Flight();
        f1.setId("ECF1");
        f1.setCategory(FlightCategory.BUSINESS);
        f1.setPassengers(new HashSet<>());

        //When
        Mockito.when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        Mockito.when(passengerRepository.findById(p1.getId()))
                .then(invocationOnMock -> Optional.of(p1));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", f1.getId(), p1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    //remove
    @Test
    @DisplayName("Given Economic Passenger When remove from Economic Flight Then is removed")
    void givenEcPassengerWhenRemoveFromEcFlightThenIsRemoved() throws Exception {

        //Given
        Passenger p1 = new Passenger();
        p1.setId("P1");
        p1.setName("Fulano");
        p1.setVip(false);

        Flight f1 = new Flight();
        f1.setId("ECF1");
        f1.setCategory(FlightCategory.ECONOMIC);
        f1.setPassengers(new HashSet<>());
        f1.getPassengers().add(p1);

        //When
        when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        when(passengerRepository.findById(p1.getId()))
                .then(invocationOnMock -> Optional.of(p1));

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                                .delete("/boarding/{flightId}/{passengerId}", f1.getId(), p1.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

    }

    @Test
    @DisplayName("Given VIP Passenger When remove from Economic Flight Then is not removed")
    void givenVipPassengerWhenRemoveFromEcFlightThenIsRemoved() throws Exception {

        //Given
        Passenger p1 = new Passenger();
        p1.setId("P1");
        p1.setName("Ciclano");
        p1.setVip(true);

        Flight f1 = new Flight();
        f1.setId("VPF1");
        f1.setCategory(FlightCategory.ECONOMIC);
        f1.setPassengers(new HashSet<>());
        f1.getPassengers().add(p1);

        //When
        when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        when(passengerRepository.findById(p1.getId()))
                .then(invocationOnMock -> Optional.of(p1));

        //Then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/boarding/{flightId}/{passengerId}", f1.getId(), p1.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

    }

    @Test
    @DisplayName("Given VIP Passenger When remove from VIP Flight Then is not removed")
    void givenVipPassengerWhenRemoveFromVipFlightThenIsNotAcceptable() throws Exception {

        //Given
        Passenger p1 = new Passenger();
        p1.setId("P1");
        p1.setName("Ciclano");
        p1.setVip(true);

        Flight f1 = new Flight();
        f1.setId("VPF1");
        f1.setCategory(FlightCategory.BUSINESS);
        f1.setPassengers(new HashSet<>());
        f1.getPassengers().add(p1);

        //When
        when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        when(passengerRepository.findById(p1.getId()))
                .then(invocationOnMock -> Optional.of(p1));

        //Then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/boarding/{flightId}/{passengerId}", f1.getId(), p1.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());

    }

    //exceptions
    @Test
    @DisplayName("Given Null Flight When add Economic Passenger Then is Conflict")
    void givenNullFlightWhenAddEcPassengerThenIsConflict() throws Exception {

        //Given
        Passenger passenger = new Passenger();
        passenger.setId("ECP1");
        passenger.setName("John Anotherson");
        passenger.setVip(false);

        //When
        Mockito.when(passengerRepository.findById(passenger.getId()))
                .then(invocationOnMock -> Optional.of(passenger));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", "Fake", passenger.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Given Null Flight When add VIP Passenger Then is Conflict")
    void givenNullFlightWhenAddVipPassengerThenIsConflict() throws Exception {

        //Given
        Passenger passenger = new Passenger();
        passenger.setName("Uthred Ragnason");
        passenger.setId("VP1");
        passenger.setVip(true);

        //When
        Mockito.when(passengerRepository.findById(passenger.getId()))
                .then(invocationOnMock -> Optional.of(passenger));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", passenger.getId(), "Fake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Given Null Passenger When add to Economic Flight Then is Conflict")
    void givenNullPassengerWhenAddToEcFlightThenEcPassengerIsAdd() throws Exception {

        //Given

        Flight f1 = new Flight();
        f1.setId("ECF1");
        f1.setCategory(FlightCategory.ECONOMIC);
        f1.setPassengers(new HashSet<>());
        //f1.getPassengers().add(p1);

        //When
        Mockito.when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", f1.getId(), "Fake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Given Null Passenger When add to VIP Flight Then is Conflict")
    void givenNullPassengerWhenAddToVipFlightThenEcPassengerIsAdd() throws Exception {

        //Given
        Flight f1 = new Flight();
        f1.setId("VPF1");
        f1.setCategory(FlightCategory.BUSINESS);
        f1.setPassengers(new HashSet<>());
        //f1.getPassengers().add(p1);

        //When
        Mockito.when(flightRepository.findById(f1.getId()))
                .then(invocationOnMock -> Optional.of(f1));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/boarding/{flightId}/{passengerId}", f1.getId(), "Fake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    //exceptions remove
    @Test
    @DisplayName("Given Passenger When remove Null Flight Then is removed")
    void givenEcPassengerWhenRemoveFromNullFlightThenIsRemoved() throws Exception {

        //Given
        Passenger passenger = new Passenger();
        passenger.setName("John Otherson");
        passenger.setId("EC1");
        passenger.setVip(false);

        //When
        Mockito.when(passengerRepository.findById(any()))
                .then(invocationOnMock -> Optional.of(passenger));

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/boarding/{flightId}/{passengerId}", "fake", passenger.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Given Null Passenger When remove from Flight Then is not removed")
    void givenVipPassengerWhenRemoveFromNullFlightThenIsRemoved() throws Exception {

        Flight flight = new Flight();
        flight.setId("ECF1");
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setPassengers(new HashSet<>());

        Mockito.when(flightRepository.findById(any()))
                .then(invocationOnMock -> Optional.of(flight));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/boarding/{flightId}/{passengerId}", flight.getId(), "fake")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());


    }
}