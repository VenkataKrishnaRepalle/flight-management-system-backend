package com.learning.fms.Controller;

import com.learning.fms.entity.Flight;
import com.learning.fms.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{uuid}")
    public ResponseEntity<Flight> getById(@PathVariable UUID uuid) {
        return new ResponseEntity<>(flightService.getById(uuid), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Flight>> getAll() {
        return new ResponseEntity<>(flightService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Flight> create(@RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.create(flight), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{uuid}")
    public ResponseEntity<Flight> update(@PathVariable UUID uuid, @RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.update(uuid, flight), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        flightService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}