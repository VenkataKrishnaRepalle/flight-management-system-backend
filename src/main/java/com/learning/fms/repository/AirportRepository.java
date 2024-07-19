package com.learning.fms.repository;

import com.learning.fms.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirportRepository extends JpaRepository<Airport, UUID> {

    Airport findByCode(String code);
}
