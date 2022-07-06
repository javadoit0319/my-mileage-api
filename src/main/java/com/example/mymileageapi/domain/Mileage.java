package com.example.mymileageapi.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(catalog = "triple", name = "mileage", indexes = @Index(columnList = "userId"))
@EntityListeners(AuditingEntityListener.class)
public class Mileage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "BINARY(16)", unique = true)
    private UUID userId;
    private int mileage;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Mileage() {}

    @Builder
    private Mileage(UUID userId, int mileage) {
        this.userId = userId;
        this.mileage = mileage;
    }

    public static Mileage of(UUID userId, int calculatedMileage) {
        return new Mileage(userId, calculatedMileage);
    }

    public void earnMileage(int mileage) {
        this.mileage += mileage;
    }

    public void useMileage(int mileage) {
        this.mileage -= mileage;
    }

    public void withdrawalMileage(int mileage) {
        this.mileage -= mileage;
    }
}
