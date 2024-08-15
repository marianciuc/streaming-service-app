package com.mv.streamingservice.user.services;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
