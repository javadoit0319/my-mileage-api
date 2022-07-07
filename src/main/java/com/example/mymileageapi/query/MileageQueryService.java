package com.example.mymileageapi.query;

import com.example.mymileageapi.domain.Mileage;
import com.example.mymileageapi.domain.MileageRepository;
import com.example.mymileageapi.exception.MileageNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MileageQueryService {

    private final MileageRepository mileageRepository;

    public MileageQueryService(MileageRepository mileageRepository) {
        this.mileageRepository = mileageRepository;
    }

    @Transactional(readOnly = true)
    public MileageQueryResponse getMileage(MileageQueryRequest request) {
        Mileage savedMileage = mileageRepository.findByUserId(request.userId())
                .orElseThrow(() -> new MileageNotFoundException(request.userId().toString()));

        return MileageQueryResponse.builder()
                .userId(savedMileage.getUserId())
                .currentMileage(savedMileage.getMileage())
                .build();
    }
}
