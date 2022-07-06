package com.example.mymileageapi.api;

import com.example.mymileageapi.app.EventRequest;
import com.example.mymileageapi.app.MileageServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MileageApi {

    private final MileageServiceFactory mileageServiceFactory;

    public MileageApi(MileageServiceFactory mileageServiceFactory) {
        this.mileageServiceFactory = mileageServiceFactory;
    }

    @PostMapping("/events")
    public ResponseEntity createEvent(@RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(mileageServiceFactory.execute(eventRequest));
    }


}
