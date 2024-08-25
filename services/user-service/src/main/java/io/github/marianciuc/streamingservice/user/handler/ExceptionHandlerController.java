package com.mv.streamingservice.user.handler;

import com.mv.streamingservice.user.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * The ExceptionHandlerController class handles exceptions by constructing an appropriate ResponseEntity with an
 * ExceptionResponse object containing the HttpStatus and message.
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Constructs a ResponseEntity containing an ExceptionResponse object with the given HttpStatus and message.
     *
     * @param status  the HttpStatus of the ResponseEntity
     * @param message the message to be included in the ExceptionResponse object
     * @return a ResponseEntity containing an ExceptionResponse object with the given HttpStatus and message
     */
    private ResponseEntity<Object> constructResponseEntity(HttpStatus status, String message) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                message
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handle(RuntimeException ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handle(NotFoundException ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(JsonWebTokenExpiredException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handle(JsonWebTokenExpiredException ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(SecurityBadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handle(SecurityBadCredentialsException ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(UnsupportedJsonWebTokenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handle(UnsupportedJsonWebTokenException ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExist.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handle(UserAlreadyExist ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserIsBanned.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handle(UserIsBanned ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.FORBIDDEN, ex.getMessage());
    }

}