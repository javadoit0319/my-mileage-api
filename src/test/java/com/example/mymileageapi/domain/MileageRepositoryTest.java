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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("container-test")
@DisplayName("Mileage DB 테스트")
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class MileageRepositoryTest {

    @Container
    public static TestContainerDB containerDB = TestContainerDB.getInstance();

    @Autowired
    private MileageRepository mileageRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private UUID 유저ID;

    @BeforeEach
    void setUp() {
        유저ID = UUID.randomUUID();
        Mileage mileage = Mileage.of(유저ID, 10000);
        mileageRepository.save(mileage);
    }

    @Transactional
    @DisplayName("SELECT, UPDATE 테스트")
    @Test
    void dbTest() {
        Mileage 유저 = mileageRepository.findByUserId(유저ID).get();
        assertThat(유저.getUserId()).isEqualTo(유저ID);
        assertThat(유저.getCreatedAt()).isNotNull();
        assertThat(유저.getUpdatedAt()).isNull();

        유저.earnMileage(10000);
        entityManager.flush();

        Mileage 업데이트_유저 = mileageRepository.findByUserId(유저ID).get();
        assertThat(업데이트_유저.getMileage()).isEqualTo(20000);
        assertThat(업데이트_유저.getCreatedAt()).isNotNull();
        assertThat(업데이트_유저.getUpdatedAt()).isNotNull();

        유저.useMileage(5000);
        entityManager.flush();

        Mileage 업데이트_유저2 = mileageRepository.findByUserId(유저ID).get();
        assertThat(업데이트_유저2.getMileage()).isEqualTo(15000);
        assertThat(업데이트_유저2.getCreatedAt()).isNotNull();
        assertThat(업데이트_유저2.getUpdatedAt()).isNotNull();
    }


}
