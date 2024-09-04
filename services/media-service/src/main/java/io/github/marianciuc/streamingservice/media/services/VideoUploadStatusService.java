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

/**
 * Interface for managing the upload statuses of videos.
 * This service provides methods to create the upload status of a video.
 */
public interface VideoUploadStatusService {

    /**
     * Creates a video upload status entry.
     *
     * @param videoId the unique identifier of the video
     * @param message the status message associated with the video upload
     * @param statusType the type of status, which can be INFO, WARNING, or ERROR
     * @param title the title of the status entry
     */
    void createVideoUploadStatus(UUID videoId, String message, StatusType statusType, String title);
}
