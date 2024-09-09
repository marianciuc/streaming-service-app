/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoFileUploadStatusDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import io.github.marianciuc.streamingservice.media.entity.VideoUploadingStatus;
import io.github.marianciuc.streamingservice.media.enums.StatusType;

import java.util.UUID;

public record VideoFileUploadStatusDto (
        UUID id,
        String title,
        String message,
        UUID videoId,
        StatusType type
) {
    public static VideoFileUploadStatusDto toVideoFileUploadStatusDto(VideoUploadingStatus videoUploadingStatus) {
        return new VideoFileUploadStatusDto(
                videoUploadingStatus.getId(),
                videoUploadingStatus.getTitle(),
                videoUploadingStatus.getMessage(),
                videoUploadingStatus.getVideo().getId(),
                videoUploadingStatus.getType()
        );
    }
}
