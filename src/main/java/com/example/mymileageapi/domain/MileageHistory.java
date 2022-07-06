package com.example.mymileageapi.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(catalog = "triple", name = "mileage_history", indexes = @Index(columnList = "userId"))
public class MileageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;
    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;
    private int mileage;
    @Enumerated(value = EnumType.STRING)
    private MileageType type;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime approvedAt;

    protected MileageHistory() {
    }

    @Builder
    private MileageHistory(UUID userId,UUID reviewId, int mileage, MileageType type) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.mileage = mileage;
        this.type = type;
    }

    public static MileageHistory of(UUID userId, UUID reviewId, int mileage, MileageType type) {
        return new MileageHistory(userId, reviewId, mileage, type);
    }

    public static MileageHistory noChange(UUID uuid, UUID reviewId) {
        return new MileageHistory(uuid, reviewId, 0, MileageType.NO_CHANGE);
    }

    public int getMileageAmount() {
        if (isUseOrWithdrawald()) {
            return mileage * -1;
        }
        return mileage;
    }

    private boolean isUseOrWithdrawald() {
        return MileageType.USE.equals(type) || MileageType.WITHDRAWAL.equals(type);
    }
}
