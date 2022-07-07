package com.example.mymileageapi.acceptance;

import com.example.mymileageapi.app.EventRequest;

import java.util.List;
import java.util.UUID;

public class MileageTestApiReuqest {
    public static EventRequest 이벤트요청생성(String type, String action, UUID reviewId, String content, List<UUID> photoList, UUID userId, UUID placeId) {
        return EventRequest.builder()
                .type(type)
                .action(action)
                .reviewId(reviewId)
                .content(content)
                .attachedPhotoIds(photoList)
                .userId(userId)
                .placeId(placeId)
                .build();
    }
}
