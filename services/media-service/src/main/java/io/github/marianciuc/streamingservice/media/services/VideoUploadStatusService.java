/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoUploadService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.enums.StatusType;

import java.util.UUID;

public interface VideoUploadStatusService {
    void createVideoUploadStatus(UUID videoId, String message, StatusType statusType, String title);
}
