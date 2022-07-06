package com.example.mymileageapi.app;

import com.example.mymileageapi.domain.*;
import com.example.mymileageapi.exception.MileageNotFoundException;
import com.example.mymileageapi.query.MileageCalculateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModMileageService {

    private final MileageCalculateService mileageCalculateService;
    private final MileageRepository mileageRepository;
    private final MileageHistoryRepository mileageHistoryRepository;

    public ModMileageService(
            MileageCalculateService mileageCalculateService,
            MileageRepository mileageRepository,
            MileageHistoryRepository mileageHistoryRepository
    ) {
        this.mileageCalculateService = mileageCalculateService;
        this.mileageRepository = mileageRepository;
        this.mileageHistoryRepository = mileageHistoryRepository;
    }

    @Transactional
    public MileageResponse modMileage(EventRequest eventRequest) {
        int calculatedMileage = mileageCalculateService.calculate(eventRequest);

        int accumulatedMileage = mileageHistoryRepository.findAllByReviewId(eventRequest.reviewId())
                .stream()
                .mapToInt(MileageHistory::getMileageAmount)
                .sum();

        Mileage savedMileage = mileageRepository.findByUserId(eventRequest.userId())
                .orElseThrow(() -> new MileageNotFoundException(eventRequest.userId().toString()));

        MileageHistory mileageHistory = processMileage(calculatedMileage, accumulatedMileage, savedMileage, eventRequest);

        return ModMileageResponse.builder()
                .userId(mileageHistory.getUserId())
                .reviewId(mileageHistory.getReviewId())
                .mileage(mileageHistory.getMileage())
                .type(mileageHistory.getType().name())
                .currentMileage(savedMileage.getMileage())
                .build();
    }

    private MileageHistory processMileage(int calculatedMileage, int accumulatedMileage, Mileage savedMileage, EventRequest eventRequest) {
        int targetMileage = Math.abs(accumulatedMileage - calculatedMileage);
        if (accumulatedMileage > calculatedMileage) {
            return withdrawalMileage(savedMileage, eventRequest, targetMileage);
        } else if (accumulatedMileage < calculatedMileage) {
            return earnMileage(savedMileage, eventRequest, targetMileage);
        }
        return MileageHistory.noChange(eventRequest.userId(), eventRequest.reviewId());
    }

    private MileageHistory withdrawalMileage(Mileage savedMileage, EventRequest eventRequest, int targetMileage) {
        savedMileage.withdrawalMileage(targetMileage);
        return mileageHistoryRepository.save(MileageHistory.of(eventRequest.userId(), eventRequest.reviewId(), targetMileage, MileageType.WITHDRAWAL));
    }

    private MileageHistory earnMileage(Mileage savedMileage, EventRequest eventRequest, int targetMileage) {
        savedMileage.earnMileage(targetMileage);
        return mileageHistoryRepository.save(MileageHistory.of(eventRequest.userId(), eventRequest.reviewId(), targetMileage, MileageType.EARN));
    }

}
