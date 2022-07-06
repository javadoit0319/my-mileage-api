package com.example.mymileageapi.app;

import lombok.Builder;

import java.util.UUID;

public record DeleteMileageResponse(UUID userId, UUID reviewId, int mileage, String type, int currentMileage) implements MileageResponse {

    @Builder
    public DeleteMileageResponse {}
}
