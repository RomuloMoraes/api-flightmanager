package com.abccompany.flightmanager.passenger;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Passenger {
    @Id
    private String id;
    private String name;
    private boolean vip;
}
