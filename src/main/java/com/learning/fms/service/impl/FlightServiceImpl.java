package com.learning.fms.service.impl;

import com.learning.fms.entity.Flight;
import com.learning.fms.exception.FoundException;
import com.learning.fms.exception.NotFoundException;
import com.learning.fms.repository.FlightRepository;
import com.learning.fms.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public Flight create(Flight flight) {
        var flightExistsByNumber = flightRepository.findByNumber(flight.getNumber());
        if (flightExistsByNumber != null) {
            throw new FoundException("FLIGHT_FOUND", "Flight already with number: " + flight.getNumber());
        }

        flight.setUuid(UUID.randomUUID());
        flight.setCreatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        flight.setCreatedTime(Instant.now());
        flight.setUpdatedTime(Instant.now());

        return flightRepository.save(flight);
    }

    @Override
    public Flight getById(UUID uuid) {
        return flightRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("FLIGHT_NOT_FOUND", "Flight not found with id: " + uuid));
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight update(UUID uuid, Flight flight) {
        var isFlightExists = flightRepository.findById(uuid);
        if (isFlightExists.isEmpty()) {
            return create(flight);
        }
        flight.setUpdatedTime(Instant.now());

        return flightRepository.save(flight);
    }

    @Override
    public void delete(UUID uuid) {
        getById(uuid);
        flightRepository.deleteById(uuid);

        var optionalFlight = flightRepository.findById(uuid);
        if (optionalFlight.isPresent()) {
            throw new FoundException("FLIGHT_NOT_DELETED", "Flight not deleted with id: " + uuid);
        }
    }
}
