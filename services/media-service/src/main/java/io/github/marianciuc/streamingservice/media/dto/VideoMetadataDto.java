/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoMetadataDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import io.github.marianciuc.streamingservice.media.entity.MediaType;

import java.util.UUID;

public record VideoMetadataDto (
        UUID id,
        ResolutionMessage resolution,
        UUID contentId,
        MediaType mediaType
){
}
