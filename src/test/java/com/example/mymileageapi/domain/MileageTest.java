package com.example.mymileageapi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("마일리지 테스트")
class MileageTest {

    @DisplayName("마일리지 생성 테스트")
    @Test
    void create() {
        Mileage mileage = Mileage.of(UUID.randomUUID(), 10000);
        assertThat(mileage.getMileage()).isEqualTo(10000);
    }

    @DisplayName("마일리지 적립 테스트")
    @Test
    void earn() {
        Mileage mileage = Mileage.of(UUID.randomUUID(), 10000);
        mileage.earnMileage(5000);
        assertThat(mileage.getMileage()).isEqualTo(15000);
    }

    @DisplayName("마일리지 사용 테스트")
    @Test
    void use() {
        Mileage mileage = Mileage.of(UUID.randomUUID(), 10000);
        mileage.useMileage(5000);
        assertThat(mileage.getMileage()).isEqualTo(5000);
    }

    @DisplayName("마일리지 회수 테스트")
    @Test
    void withdrawal() {
        Mileage mileage = Mileage.of(UUID.randomUUID(), 10000);
        mileage.withdrawalMileage(5000);
        assertThat(mileage.getMileage()).isEqualTo(5000);
    }
}
