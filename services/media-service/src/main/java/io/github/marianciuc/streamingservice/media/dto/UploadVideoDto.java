/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UploadVideoDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import io.github.marianciuc.streamingservice.media.enums.RecordStatus;

import java.util.UUID;

public record UploadVideoDto (
        UUID contentId,
        RecordStatus recordStatus,
        UUID masterPlaylistId
) {
}
