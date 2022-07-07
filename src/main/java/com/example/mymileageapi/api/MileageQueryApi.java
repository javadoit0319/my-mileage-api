package com.example.mymileageapi.api;

import com.example.mymileageapi.query.MileageQueryRequest;
import com.example.mymileageapi.query.MileageQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MileageQueryApi {

    private final MileageQueryService mileageQueryService;

    public MileageQueryApi(MileageQueryService mileageQueryService) {
        this.mileageQueryService = mileageQueryService;
    }

    @GetMapping("/mileage")
    public ResponseEntity getMileage(MileageQueryRequest request) {
        return ResponseEntity.ok(mileageQueryService.getMileage(request));
    }

}
