package io.github.marianciuc.streamingservice.user.exceptions;

public class JsonWebTokenExpiredException extends RuntimeException{
    public JsonWebTokenExpiredException(String message) {
        super(message);
    }
}
