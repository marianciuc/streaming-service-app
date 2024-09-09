/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MetadataDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import java.util.UUID;

public record UploadMetadataDto(
        UUID id,
        int totalChunks
) {
}
