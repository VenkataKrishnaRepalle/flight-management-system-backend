package com.learning.fms.service;

import com.learning.fms.entity.Flight;

import java.util.List;
import java.util.UUID;

public interface FlightService {
    Flight create(Flight flight);

    Flight getById(UUID uuid);

    List<Flight> getAll();

    Flight update(UUID uuid, Flight flight);

    void delete(UUID uuid);
}
