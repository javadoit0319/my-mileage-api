package com.example.mymileageapi.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

public interface MileageRepository extends JpaRepository<Mileage, Long> {
    @Lock(value = LockModeType.OPTIMISTIC)
    Optional<Mileage> findByUserId(UUID userId);
}
