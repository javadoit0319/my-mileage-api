package com.example.mymileageapi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("마일리지 히스토리 테스트")
class MileageHistoryTest {

    @DisplayName("마일리지 히스토리 생성")
    @Test
    void create() {
        MileageHistory mileageHistory = MileageHistory.of(UUID.randomUUID(), UUID.randomUUID(), 10000, MileageType.EARN);
        assertThat(mileageHistory.getMileage()).isEqualTo(10000);
    }

    @DisplayName("마일리지 변화 없는 히스토리 생성")
    @Test
    void noChangeCreate() {
        MileageHistory mileageHistory = MileageHistory.noChange(UUID.randomUUID(), UUID.randomUUID());
        assertThat(mileageHistory.getMileage()).isEqualTo(0);
        assertThat(mileageHistory.getType()).isEqualTo(MileageType.NO_CHANGE);
    }

    @DisplayName("마일리지 타입이 사용,회수면 음수 반환")
    @Test
    void getMileageAmount() {
        MileageHistory mileageHistory = MileageHistory.of(UUID.randomUUID(), UUID.randomUUID(), 10000, MileageType.USE);
        assertThat(mileageHistory.getMileageAmount()).isEqualTo(-10000);

        MileageHistory mileageHistory2 = MileageHistory.of(UUID.randomUUID(), UUID.randomUUID(), 5000, MileageType.WITHDRAWAL);
        assertThat(mileageHistory2.getMileageAmount()).isEqualTo(-5000);
    }

}
