package com.example.mymileageapi.app;

import com.example.mymileageapi.domain.*;
import com.example.mymileageapi.query.MileageCalculateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddMileageService {

    private final MileageCalculateService mileageCalculateService;
    private final MileageRepository mileageRepository;
    private final MileageHistoryRepository mileageHistoryRepository;

    public AddMileageService(
            MileageCalculateService mileageCalculateService,
            MileageRepository mileageRepository,
            MileageHistoryRepository mileageHistoryRepository) {
        this.mileageCalculateService = mileageCalculateService;
        this.mileageRepository = mileageRepository;
        this.mileageHistoryRepository = mileageHistoryRepository;
    }

    @Transactional
    public AddMileageResponse addMileage(EventRequest eventRequest) {
        int calculatedMileage = mileageCalculateService.calculate(eventRequest);
        Mileage mileage = mileageRepository.findByUserId(eventRequest.userId())
                .orElse(Mileage.of(eventRequest.userId(), 0));
        mileage.earnMileage(calculatedMileage);

        Mileage savedMileage = mileageRepository.save(mileage);

        MileageHistory savedHistory = mileageHistoryRepository.save(
                MileageHistory.of(
                        eventRequest.userId(),
                        eventRequest.reviewId(),
                        calculatedMileage,
                        MileageType.EARN)
        );

        return AddMileageResponse.builder()
                .userId(savedHistory.getUserId())
                .reviewId(savedHistory.getReviewId())
                .mileage(savedHistory.getMileage())
                .type(savedHistory.getType().name())
                .currentMileage(savedMileage.getMileage())
                .build();

    }
}
