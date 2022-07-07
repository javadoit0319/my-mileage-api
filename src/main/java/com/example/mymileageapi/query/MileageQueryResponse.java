package com.example.mymileageapi.query;

import lombok.Builder;

import java.util.UUID;

public record MileageQueryResponse(UUID userId, int currentMileage) {
    @Builder
    public MileageQueryResponse {}
}
