package com.example.mymileageapi.domain;

import lombok.Builder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(catalog = "triple", name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "BINARY(16)")
    private UUID photoId;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    protected Photo() {}

    @Builder
    public Photo(UUID photoId, String imgUrl, Review review) {
        this.photoId = photoId;
        this.imgUrl = imgUrl;
        this.review = review;
    }

    public void addReview(Review review) {
        this.review = review;
    }
}
