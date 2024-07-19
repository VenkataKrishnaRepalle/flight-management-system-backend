package com.learning.fms.service;

import com.learning.fms.entity.Airport;

import java.util.List;
import java.util.UUID;

public interface AirportService {
    List<Airport> getAll();

    Airport getById(UUID uuid);

    Airport create(Airport airport);

    Airport update(UUID uuid, Airport airport);

    void delete(UUID uuid);
}
