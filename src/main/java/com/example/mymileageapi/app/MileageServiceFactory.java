package com.example.mymileageapi.app;

import org.springframework.stereotype.Component;

@Component
public class MileageServiceFactory {

    private final AddMileageService addMileageService;
    private final ModMileageService modMileageService;
    private final DeleteMileageService deleteMileageService;

    public MileageServiceFactory(
            AddMileageService addMileageService,
            ModMileageService modMileageService,
            DeleteMileageService deleteMileageService) {
        this.addMileageService = addMileageService;
        this.modMileageService = modMileageService;
        this.deleteMileageService = deleteMileageService;
    }

    public MileageResponse execute(EventRequest eventRequest) {
        if (eventRequest.isReviewAddEvent()) {
            return addMileageService.addMileage(eventRequest);
        }

        if (eventRequest.isReviewModEvent()) {
            return modMileageService.modMileage(eventRequest);
        }

        return deleteMileageService.deleteMileage(eventRequest);
    }
}
