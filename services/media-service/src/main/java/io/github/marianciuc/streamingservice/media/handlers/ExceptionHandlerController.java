package io.github.marianciuc.streamingservice.media.handlers;

import io.github.marianciuc.streamingservice.media.dto.ExceptionResponse;
import io.github.marianciuc.streamingservice.media.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The ExceptionHandlerController class handles exceptions by constructing an appropriate ResponseEntity with an
 * ExceptionResponse object containing the HttpStatus and message.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = new HashMap<>();

    static {
        EXCEPTION_STATUS_MAP.put(ChunkUploadNotInitializedException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(ChunkUploadTimeoutException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(CompressingException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(ForbiddenException.class, HttpStatus.FORBIDDEN);
        EXCEPTION_STATUS_MAP.put(ImageNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS_MAP.put(ImageUploadException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(NotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS_MAP.put(VideoAssembleException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(VideoDeleteException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(VideoStorageException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(VideoUploadException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(RuntimeException.class, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Constructs a ResponseEntity containing an ExceptionResponse object with the given HttpStatus and message.
     *
     * @param status  the HttpStatus of the ResponseEntity
     * @param message the message to be included in the ExceptionResponse object
     * @return a ResponseEntity containing an ExceptionResponse object with the given HttpStatus and message
     */
    private ResponseEntity<Object> createResponseEntity(HttpStatus status, String message) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), message);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        return createResponseEntity(status, ex.getMessage());
    }
}