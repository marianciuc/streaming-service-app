/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ConvertingTaskDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka.messages;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.dto.VideoFileMetadataDto;

import java.util.UUID;

public record StartConvertingMessage(
        UUID metadataId,
        UUID videoId,
        ResolutionDto resolution
) {
}
