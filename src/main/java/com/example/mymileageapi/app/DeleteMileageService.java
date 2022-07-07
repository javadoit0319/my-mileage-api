package com.example.mymileageapi.app;

import com.example.mymileageapi.domain.*;
import com.example.mymileageapi.exception.MileageNotFoundException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMileageService {

    private final MileageHistoryRepository mileageHistoryRepository;
    private final MileageRepository mileageRepository;

    public DeleteMileageService(
            MileageHistoryRepository mileageHistoryRepository,
            MileageRepository mileageRepository) {
        this.mileageHistoryRepository = mileageHistoryRepository;
        this.mileageRepository = mileageRepository;
    }

    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Transactional
    public MileageResponse deleteMileage(EventRequest eventRequest) {
        int accumulatedMileage = mileageHistoryRepository.findAllByReviewId(eventRequest.reviewId())
                .stream()
                .mapToInt(MileageHistory::getMileageAmount)
                .sum();

        Mileage savedMileage = mileageRepository.findByUserId(eventRequest.userId())
                .orElseThrow(() -> new MileageNotFoundException(eventRequest.userId().toString()));
        savedMileage.withdrawalMileage(accumulatedMileage);

        MileageHistory savedHistory = mileageHistoryRepository.save(
                MileageHistory.of(
                        eventRequest.userId(),
                        eventRequest.reviewId(),
                        accumulatedMileage,
                        MileageType.WITHDRAWAL)
        );
        return DeleteMileageResponse.builder()
                .userId(savedHistory.getUserId())
                .reviewId(savedHistory.getReviewId())
                .mileage(savedHistory.getMileage())
                .type(savedHistory.getType().name())
                .currentMileage(savedMileage.getMileage())
                .build();
    }
}
