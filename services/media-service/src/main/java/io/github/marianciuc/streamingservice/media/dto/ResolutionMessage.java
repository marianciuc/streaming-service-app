/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionResponse.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import java.util.UUID;

public record ResolutionMessage(
        UUID id,
        String description,
        String name
) {
}
