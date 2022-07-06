package com.example.mymileageapi.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public record EventRequest(
        String type,
        String action,
        UUID reviewId,
        String content,
        List<UUID> attachedPhotoIds,
        UUID userId,
        UUID placeId
) {

    @Builder
    public EventRequest {}

    @JsonIgnore
    public boolean isReviewAddEvent() {
        return type.equals(EventType.REVIEW.name()) && action.equals(ActionType.ADD.name());
    }

    @JsonIgnore
    public boolean isReviewModEvent() {
        return type.equals(EventType.REVIEW.name()) && action.equals(ActionType.MOD.name());
    }

    @JsonIgnore
    public boolean isContentLengthMoreOne() {
        return content.length() >= 1;
    }

    @JsonIgnore
    public boolean isAddPhotoMoreOne() {
        return attachedPhotoIds.size() >= 1;
    }

}
