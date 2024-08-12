package com.mv.streamingservice.content.handler;

import com.mv.streamingservice.content.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Constructs a ResponseEntity containing an ExceptionResponse object with the given HttpStatus and message.
     *
     * @param status  the HttpStatus of the ResponseEntity
     * @param message the message to be included in the ExceptionResponse object
     * @return a ResponseEntity containing an ExceptionResponse object with the given HttpStatus and message
     */
    private ResponseEntity<ExceptionResponse> constructResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(message, LocalDateTime.now()));
    }
}
