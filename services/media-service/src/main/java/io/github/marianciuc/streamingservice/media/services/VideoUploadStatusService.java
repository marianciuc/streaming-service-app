/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoUploadService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.VideoFileUploadStatusDto;
import io.github.marianciuc.streamingservice.media.enums.StatusType;
import io.github.marianciuc.streamingservice.media.validation.VideoFile;

import java.util.List;
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


    /**
     * Retrieves the upload status of a specified video.
     *
     * @param videoId the unique identifier of the video
     * @return a list of VideoFileUploadStatusDto objects representing the upload statuses of the video
     * @throws IllegalArgumentException if the video with the given id does not exist
     */
    List<VideoFileUploadStatusDto> getVideoUploadStatus(UUID videoId);
}
