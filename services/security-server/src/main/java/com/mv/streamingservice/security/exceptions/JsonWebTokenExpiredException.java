package com.mv.streamingservice.security.exceptions;

public class JsonWebTokenExpiredException extends RuntimeException {
    public JsonWebTokenExpiredException(String message) {}
    public JsonWebTokenExpiredException(String message, RuntimeException exception) {}
}
