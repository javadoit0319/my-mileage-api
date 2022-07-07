package com.example.mymileageapi.domain;

import com.example.mymileageapi.utils.TestContainerDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("container-test")
@DisplayName("Review DB 테스트")
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Container
    public static TestContainerDB containerDB = TestContainerDB.getInstance();

    @Autowired
    private ReviewRepository reviewRepository;
    private UUID 장소ID = UUID.randomUUID();
    private Review 리뷰2;
    private Review 리뷰1;

    @BeforeEach
    void setUp() {
        Review review1 = createReview(UUID.randomUUID(), "컨텐츠1", UUID.randomUUID(), 장소ID);
        Photo photo = createPhoto(review1);
        review1.addPhoto(photo);
        리뷰1 = reviewRepository.save(review1);

        Review review2 = createReview(UUID.randomUUID(), "컨텐츠2", UUID.randomUUID(), 장소ID);
        Photo photo2 = createPhoto(review2);
        review2.addPhoto(photo2);
        리뷰2 = reviewRepository.save(review2);
    }

    @DisplayName("SELECT 테스트")
    @Test
    @Transactional
    void DbTest() {
        Review review = reviewRepository.findByReviewId(리뷰1.getReviewId()).get();
        assertThat(review.getId()).isEqualTo(리뷰1.getId());
        assertThat(review.getContents()).isEqualTo(리뷰1.getContents());
        assertThat(review.getAttachedPhotoIds().size()).isEqualTo(리뷰1.getAttachedPhotoIds().size());
        assertThat(review.getPlaceId()).isEqualTo(장소ID);

        Review review2 = reviewRepository.findByReviewId(리뷰2.getReviewId()).get();
        assertThat(review2.getId()).isEqualTo(리뷰2.getId());
        assertThat(review2.getContents()).isEqualTo(리뷰2.getContents());
        assertThat(review2.getAttachedPhotoIds().size()).isEqualTo(리뷰2.getAttachedPhotoIds().size());
        assertThat(review2.getPlaceId()).isEqualTo(장소ID);

        List<Review> reviewList = reviewRepository.findAllByPlaceId(장소ID);
        assertThat(reviewList.size()).isEqualTo(2);

        boolean exist = reviewRepository.existsByPlaceId(장소ID);
        assertThat(exist).isTrue();

        boolean exist2 = reviewRepository.existsByPlaceId(UUID.fromString(장소ID.toString()));
        assertThat(exist2).isTrue();
    }

    private Review createReview(UUID reviewId, String contents, UUID userId, UUID placeId) {
        return Review.builder()
                .reviewId(reviewId)
                .contents(contents)
                .userId(userId)
                .placeId(placeId)
                .build();
    }

    private Photo createPhoto(Review review) {
        return Photo.builder()
                .review(review)
                .build();
    }
}
