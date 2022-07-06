package com.example.mymileageapi.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
@Table(catalog = "triple", name = "review", indexes = @Index(columnList = "placeId"))
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;
    @Lob
    private String contents;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<Photo> attachedPhotoIds;
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;
    @Column(columnDefinition = "BINARY(16)")
    private UUID placeId;

    protected Review() {}

    @Builder
    public Review(UUID reviewId, String contents, UUID userId, UUID placeId) {
        this.reviewId = reviewId;
        this.contents = contents;
        this.attachedPhotoIds = new ArrayList<>();
        this.userId = userId;
        this.placeId = placeId;
    }

    public void addPhoto(Photo photo) {
        this.attachedPhotoIds.add(photo);
        photo.addReview(this);
    }
}
