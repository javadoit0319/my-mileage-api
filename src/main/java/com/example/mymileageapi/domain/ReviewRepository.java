package com.example.mymileageapi.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByReviewId(UUID userId);
    List<Review> findAllByPlaceId(UUID placeId);
    boolean existsByPlaceId(UUID placeId);
}
