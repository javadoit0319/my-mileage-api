package com.example.mymileageapi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MileageHistoryRepository extends JpaRepository<MileageHistory, Long> {
    //Optional<MileageHistory> findFirstByReviewIdOrderByApprovedAtDesc(UUID reviewId);
    List<MileageHistory> findAllByReviewId(UUID reviewId);
}
