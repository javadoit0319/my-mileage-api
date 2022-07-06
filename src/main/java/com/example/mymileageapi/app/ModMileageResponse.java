package com.example.mymileageapi.app;

import lombok.Builder;

import java.util.UUID;

public record ModMileageResponse(UUID userId, UUID reviewId, int mileage, String type, int currentMileage) implements MileageResponse  {

    @Builder
    public ModMileageResponse {}
}
