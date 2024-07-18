package com.learning.fms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Flight {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "number", unique = true)
    private String number;

    @Column(name = "carrier_name")
    private String carrierName;

    @Column(name = "model")
    private String model;

    @Column(name = "seat_capacity")
    private int seatCapacity;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_time", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdTime;

    @Column(name = "updated_time", nullable = false)
    @UpdateTimestamp
    private Instant updatedTime;
}
