package com.example.mymileageapi.acceptance;

import com.example.mymileageapi.app.EventRequest;
import com.example.mymileageapi.query.MileageQueryRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class MileageTestApiClient {

    public static ExtractableResponse<Response> 이벤트API호출(EventRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/events")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 마일리지조회API호출(MileageQueryRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("userId", request.userId())
                .body(request)
                .when().get("/mileage")
                .then().log().all()
                .extract();
    }

}
