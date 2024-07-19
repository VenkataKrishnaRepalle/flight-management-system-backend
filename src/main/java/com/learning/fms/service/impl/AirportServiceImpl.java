package com.learning.fms.service.impl;

import com.learning.fms.entity.Airport;
import com.learning.fms.exception.FoundException;
import com.learning.fms.exception.NotFoundException;
import com.learning.fms.repository.AirportRepository;
import com.learning.fms.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport getById(UUID uuid) {
        return airportRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("AIRPORT_NOT_FOUND", "Airport not found with id: " + uuid));
    }

    @Override
    public Airport create(Airport airport) {
        var isAirportExists = airportRepository.findByCode(airport.getCode());
        if (isAirportExists != null) {
            throw new FoundException("AIRPORT_ALREADY_EXISTS", "Airport with code '" + airport.getCode() + "' already exists");
        }
        airport.setUuid(UUID.randomUUID());
        airport.setCreatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        airport.setCreatedTime(Instant.now());
        airport.setUpdatedTime(Instant.now());

        return airportRepository.save(airport);
    }

    @Override
    public Airport update(UUID uuid, Airport airport) {
        var isAirportExists = airportRepository.findById(uuid);
        if (isAirportExists.isEmpty()) {
            return create(airport);
        }
        airport.setUpdatedTime(Instant.now());

        return airportRepository.save(airport);
    }

    @Override
    public void delete(UUID uuid) {
        getById(uuid);
        airportRepository.deleteById(uuid);

        var optionalAirport = airportRepository.findById(uuid);
        if (optionalAirport.isPresent()) {
            throw new NotFoundException("AIRPORT_NOT_FOUND", "Airport not found with id: " + uuid);
        }
    }
}
