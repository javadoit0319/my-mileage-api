package com.example.mymileageapi.app;

import com.example.mymileageapi.domain.MileageType;
import lombok.Builder;

import java.util.UUID;

public record AddMileageResponse(UUID userId, UUID reviewId, int mileage, String type,  int currentMileage) implements MileageResponse {

    @Builder
    public AddMileageResponse {}
}
