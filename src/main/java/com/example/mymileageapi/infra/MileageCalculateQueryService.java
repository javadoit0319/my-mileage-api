package com.example.mymileageapi.infra;

import com.example.mymileageapi.app.EventRequest;
import com.example.mymileageapi.domain.ReviewRepository;
import com.example.mymileageapi.query.MileageCalculateService;
import org.springframework.stereotype.Service;

@Service
public class MileageCalculateQueryService implements MileageCalculateService {

    private final ReviewRepository reviewRepository;

    public MileageCalculateQueryService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public int calculate(EventRequest eventRequest) {
        int point = 0;

        if (eventRequest.isContentLengthMoreOne()) {
            point++;
        }

        if (eventRequest.isAddPhotoMoreOne()) {
            point++;
        }

        if (isFirstReview(eventRequest)) {
            point++;
        }

        return point;
    }

    private boolean isFirstReview(EventRequest eventRequest) {
        return !reviewRepository.existsByPlaceId(eventRequest.placeId());
    }
}
