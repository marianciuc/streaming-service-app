/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionMessage.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka.messages;

import io.github.marianciuc.streamingservice.media.entity.Resolution;

import java.util.UUID;

public record ResolutionMessage (
        UUID id,
        Integer height,
        String name
) {
    public static ResolutionMessage toResolutionMessage(Resolution resolution) {
        return new ResolutionMessage(resolution.getId(), resolution.getHeight(), resolution.getName());
    }
}
