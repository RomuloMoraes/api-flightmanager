package com.abccompany.flightmanager.passenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassengerController.class)
//@ExtendWith(MockitoExtension.class)
class PassengerControllerTest {

    @MockBean
    PassengerRepository passengerRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAll() {

    }

    @Test
    @DisplayName("Given Economic Passenger When Get the ID Then Passenger is Get")
    void givenEcPassengerWhenGetIDThenPassengerIsGet() throws Exception {

        //Given
        Passenger passenger = new Passenger();
        passenger.setId("P1");
        passenger.setName("Fulano");
        passenger.setVip(false);

        //When
        Mockito.when(passengerRepository.findById(passenger.getId()))
                .then(invocationOnMock -> Optional.of(passenger));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/passenger/{id}", passenger.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Given Create Economic Passenger When Get the ID Then Passenger is Get")
    void shouldCreatePassenger2() throws Exception {

        //Given
        Passenger passenger = new Passenger();
        passenger.setId("P2");
        passenger.setName("Fulano");
        passenger.setVip(false);

        //When
        Mockito.when(passengerRepository.findByName(passenger.getName()))
                .then(invocationOnMock -> Optional.empty());
        Mockito.when(passengerRepository.save(passenger))
                .then(invocationOnMock -> passenger);

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passenger))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void retrieve() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}