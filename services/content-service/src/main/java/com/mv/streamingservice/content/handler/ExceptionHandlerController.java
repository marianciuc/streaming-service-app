package com.mv.streamingservice.content.handler;

import com.mv.streamingservice.content.dto.response.ExceptionResponse;
import com.mv.streamingservice.content.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * The ExceptionHandlerController class is responsible for handling exceptions thrown by RESTful APIs
 * and constructing appropriate ResponseEntity objects.
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
    private ResponseEntity<ExceptionResponse> constructResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(message, LocalDateTime.now()));
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return constructResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
