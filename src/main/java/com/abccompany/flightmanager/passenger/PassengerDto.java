package com.abccompany.flightmanager.passenger;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PassengerDto {
    private String id;
    private String name;
    private boolean vip;

    public static PassengerDto from(Passenger passenger) {
        PassengerDto dto = new PassengerDto();
        dto.id = passenger.getId();
        dto.name = passenger.getName();
        dto.vip = passenger.isVip();
        return dto;
    }

    public Passenger toPassenger() {
        Passenger passenger = new Passenger();
        passenger.setId(id);
        passenger.setName(name);
        passenger.setVip(vip);
        return passenger;
    }
}
