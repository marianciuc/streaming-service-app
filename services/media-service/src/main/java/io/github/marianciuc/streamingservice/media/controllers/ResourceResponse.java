package io.github.marianciuc.streamingservice.media.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

public record ResourceResponse(
        HttpStatus status,
        String contentType,
        String rangeLength,
        Long rangeStart,
        Long rangeEnd,
        Long fileLength,
        Resource resource
) {
}
