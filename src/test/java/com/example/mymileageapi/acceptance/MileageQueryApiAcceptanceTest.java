package com.example.mymileageapi.acceptance;

import com.example.mymileageapi.app.AddMileageResponse;
import com.example.mymileageapi.app.EventRequest;
import com.example.mymileageapi.domain.MileageType;
import com.example.mymileageapi.query.MileageQueryRequest;
import com.example.mymileageapi.query.MileageQueryResponse;
import com.example.mymileageapi.utils.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.example.mymileageapi.acceptance.MileageTestApiClient.마일리지조회API호출;
import static com.example.mymileageapi.acceptance.MileageTestApiClient.이벤트API호출;
import static com.example.mymileageapi.acceptance.MileageTestApiReuqest.이벤트요청생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("마일리지 조회 인수테스트")
public class MileageQueryApiAcceptanceTest extends AcceptanceTest {

    private UUID 유저ID;

    @BeforeEach
    public void setUp() {
        super.setUp();

        유저ID = UUID.randomUUID();
        UUID 리뷰ID = UUID.randomUUID();
        UUID 포토ID_1 = UUID.randomUUID();
        UUID 포토ID_2 = UUID.randomUUID();
        UUID 장소ID = UUID.randomUUID();

        EventRequest ADD요청 = 이벤트요청생성("REVIEW", "ADD", 리뷰ID, "컨텐츠입니다!", Arrays.asList(포토ID_1, 포토ID_2), 유저ID, 장소ID);
        ExtractableResponse<Response> ADD응답 = 이벤트API호출(ADD요청);
        마일리지적립됨(ADD응답, 3, MileageType.EARN.name(), 3);
    }

    @DisplayName("마일리지 조회 테스트")
    @Test
    void 마일리지조회테스트() {
        MileageQueryRequest 조회요청 = 조회요청생성(유저ID);
        ExtractableResponse<Response> 조회응답 = 마일리지조회API호출(조회요청);
        assertThat(조회응답.statusCode()).isEqualTo(HttpStatus.OK.value());
        MileageQueryResponse response = 조회응답.as(MileageQueryResponse.class);
        assertThat(response.userId()).isEqualTo(유저ID);
        assertThat(response.currentMileage()).isEqualTo(3);
    }

    private MileageQueryRequest 조회요청생성(UUID userId) {
        return MileageQueryRequest.builder()
                .userId(userId)
                .build();
    }

    private void 마일리지적립됨(ExtractableResponse<Response> response, int mileage, String type, int currentMileage) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        AddMileageResponse addMileageResponse = response.as(AddMileageResponse.class);
        assertThat(addMileageResponse.mileage()).isEqualTo(mileage);
        assertThat(addMileageResponse.type()).isEqualTo(type);
        assertThat(addMileageResponse.currentMileage()).isEqualTo(currentMileage);
    }
}
