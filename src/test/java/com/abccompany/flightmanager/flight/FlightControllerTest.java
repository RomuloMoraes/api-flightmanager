package com.abccompany.flightmanager.flight;

import com.abccompany.flightmanager.passenger.Passenger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.error.OptionalShouldBePresent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @MockBean
    private FlightRepository flightRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Flight flight;

    /*@Test
    void shouldGetAllFlightsAvailable() throws Exception {

        //Given
        Flight flight1, flight2, flight3;

        flight1 = new Flight();
        flight1.setCategory(FlightCategory.ECONOMIC);
        flight1.setId("EF2");
        flight1.setPassengers(new HashSet<Passenger>());

        flight2 = new Flight();
        flight2.setCategory(FlightCategory.ECONOMIC);
        flight2.setId("EF2");
        flight2.setPassengers(new HashSet<Passenger>());

        flight3 = new Flight();
        flight3.setCategory(FlightCategory.BUSINESS);
        flight3.setId("EF2");
        flight3.setPassengers(new HashSet<Passenger>());

        List<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);


        //When
        when(flightRepository.findAll()).thenReturn(flights);

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(flights.size()));
    }*/

    @Test
    void shouldGetAllFlightsAvailable() throws Exception {

        //Given
        HashSet<Passenger> passengersList = new HashSet<>();

        List<Flight> flights = new ArrayList<>(Arrays.asList(
                new Flight("EC1", FlightCategory.BUSINESS, passengersList),
                new Flight("EC2", FlightCategory.BUSINESS, passengersList),
                new Flight("EC3", FlightCategory.BUSINESS, passengersList)));

        //When
        when(flightRepository.findAll()).thenReturn(flights);

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(flights.size()));
    }

    @Test
    void shouldCreateVIPFlight() throws Exception {
        //Given
        flight = new Flight();
        flight.setCategory(FlightCategory.BUSINESS);
        flight.setId("EF2");
        flight.setPassengers(new HashSet<Passenger>());

        //When
        when(flightRepository.findById(flight.getId()))
                .then(invocationOnMock -> Optional.empty());
        when(flightRepository.save(flight))
                .then(invocationOnMock -> flight);

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateNewEconomicFlight() throws Exception {

        //Given
        flight = new Flight();
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setId("EF1");
        flight.setPassengers(new HashSet<Passenger>());

        //When
        when(flightRepository.findById(flight.getId()))
                .then(invocationOnMock -> Optional.empty());
        when(flightRepository.save(flight))
                .then(invocationOnMock -> flight);

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldConflictFlightAlreadyExist() throws Exception {
        //Given
        flight = new Flight();
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setId("EC3");
        flight.setPassengers(new HashSet<>());

        //When
        when(flightRepository.findById(flight.getId())).then(InvocationOnMock -> Optional.of(flight));

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldGetEconomicFlight() throws Exception {
        //Given
        flight = new Flight();
        flight.setCategory(FlightCategory.ECONOMIC);
        flight.setId("EC2");
        flight.setPassengers(new HashSet<>());

        String flightCategoryAns = String.valueOf(flight.getCategory());

        //When
        when(flightRepository.findById(flight.getId())).then(InvocationOnMock -> Optional.of(flight));

        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/flight/{id}", flight.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(flight.getId()))
                .andExpect(jsonPath("$.category").value(flightCategoryAns))
                .andDo(print());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}