/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionRequest.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

public record ResolutionRequest(
        String name,
        String description,
        Integer height,
        Integer width,
        Integer bitrate
) {
}
