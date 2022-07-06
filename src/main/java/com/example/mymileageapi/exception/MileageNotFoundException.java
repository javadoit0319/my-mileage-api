package com.example.mymileageapi.exception;

import java.util.UUID;

public class MileageNotFoundException extends RuntimeException {
    public MileageNotFoundException(String userId) {
        super("userId:" + userId + "의 적립된 마일리지가 없습니다.");
    }
}
