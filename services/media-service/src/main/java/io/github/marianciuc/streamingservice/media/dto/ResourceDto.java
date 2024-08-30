/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResourceResponse.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

public record ResourceDto(
        HttpStatus status,
        String contentType,
        String rangeLength,
        Long rangeStart,
        Long rangeEnd,
        Long fileLength,
        Resource resource
) {
}
