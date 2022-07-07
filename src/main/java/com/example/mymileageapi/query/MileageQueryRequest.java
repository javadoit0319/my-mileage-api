package com.example.mymileageapi.query;

import lombok.Builder;

import java.util.UUID;

public record MileageQueryRequest(UUID userId) {
    @Builder
    public MileageQueryRequest {}
}
