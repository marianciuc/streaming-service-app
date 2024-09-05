/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MovieDto.java
 *
 */

package io.github.marianciuc.streamingservice.content.dto;

import java.util.UUID;

public record MovieDto(
        UUID id,
        UUID contentId,
        int duration,
        String masterPlaylistUrl,
        UUID masterPlaylistId
) {
}
