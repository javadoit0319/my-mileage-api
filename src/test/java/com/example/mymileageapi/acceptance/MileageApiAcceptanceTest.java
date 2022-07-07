package com.example.mymileageapi.acceptance;

import com.example.mymileageapi.app.AddMileageResponse;
import com.example.mymileageapi.app.EventRequest;
import com.example.mymileageapi.domain.MileageType;
import com.example.mymileageapi.utils.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static com.example.mymileageapi.acceptance.MileageTestApiClient.이벤트API호출;
import static com.example.mymileageapi.acceptance.MileageTestApiReuqest.이벤트요청생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("마일리지 인수테스트")
public class MileageApiAcceptanceTest extends AcceptanceTest {

    @DisplayName("마일리지 생성,수정,삭제 테스트")
    @Test
    void 마일리지테스트() {
        UUID 유저ID = UUID.randomUUID();
        UUID 리뷰ID = UUID.randomUUID();
        UUID 포토ID_1 = UUID.randomUUID();
        UUID 포토ID_2 = UUID.randomUUID();
        UUID 장소ID = UUID.randomUUID();


        EventRequest ADD요청 = 이벤트요청생성("REVIEW", "ADD", 리뷰ID, "컨텐츠입니다!", Arrays.asList(포토ID_1, 포토ID_2), 유저ID, 장소ID);
        ExtractableResponse<Response> ADD응답 = 이벤트API호출(ADD요청);
        마일리지적립됨(ADD응답, 3, MileageType.EARN.name(), 3);

        EventRequest MOD요청 = 이벤트요청생성("REVIEW", "MOD", 리뷰ID, "", Collections.EMPTY_LIST, 유저ID, 장소ID);
        ExtractableResponse<Response> MOD응답 = 이벤트API호출(MOD요청);
        마일리지적립됨(MOD응답, 2, MileageType.WITHDRAWAL.name(), 1);

        EventRequest MOD요청2 = 이벤트요청생성("REVIEW", "MOD", 리뷰ID, "다시 컨텐츠를 썼어요!", Collections.EMPTY_LIST, 유저ID, 장소ID);
        ExtractableResponse<Response> MOD응답2 = 이벤트API호출(MOD요청2);
        마일리지적립됨(MOD응답2, 1, MileageType.EARN.name(), 2);

        EventRequest MOD요청3 = 이벤트요청생성("REVIEW", "MOD", 리뷰ID, "다시 컨텐츠를 썼어요!", Arrays.asList(포토ID_1), 유저ID, 장소ID);
        ExtractableResponse<Response> MOD응답3 = 이벤트API호출(MOD요청3);
        마일리지적립됨(MOD응답3, 1, MileageType.EARN.name(), 3);

        EventRequest MOD요청4 = 이벤트요청생성("REVIEW", "MOD", 리뷰ID, "다시 컨텐츠를 썼어요!", Arrays.asList(포토ID_1), 유저ID, 장소ID);
        ExtractableResponse<Response> MOD응답4 = 이벤트API호출(MOD요청4);
        마일리지적립됨(MOD응답4, 0, MileageType.NO_CHANGE.name(), 3);

        EventRequest DELETE요청 = 이벤트요청생성("REVIEW", "DELETE", 리뷰ID, "다시 컨텐츠를 썼어요!", Arrays.asList(포토ID_1), 유저ID, 장소ID);
        ExtractableResponse<Response> DELETE응답 = 이벤트API호출(DELETE요청);
        마일리지적립됨(DELETE응답, 3, MileageType.WITHDRAWAL.name(), 0);
    }

    private void 마일리지적립됨(ExtractableResponse<Response> response, int mileage, String type, int currentMileage) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        AddMileageResponse addMileageResponse = response.as(AddMileageResponse.class);
        assertThat(addMileageResponse.mileage()).isEqualTo(mileage);
        assertThat(addMileageResponse.type()).isEqualTo(type);
        assertThat(addMileageResponse.currentMileage()).isEqualTo(currentMileage);
    }
}
