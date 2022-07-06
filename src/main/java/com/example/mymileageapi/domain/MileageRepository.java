package com.example.mymileageapi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MileageRepository extends JpaRepository<Mileage, Long> {
    Optional<Mileage> findByUserId(UUID userId);
}
