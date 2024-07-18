package com.learning.fms.repository;

import com.learning.fms.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Flight findByNumber(String number);
}
