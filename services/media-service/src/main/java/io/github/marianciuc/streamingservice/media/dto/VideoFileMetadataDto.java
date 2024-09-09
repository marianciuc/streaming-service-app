/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoFileMetadataDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import io.github.marianciuc.streamingservice.media.entity.VideoFileMetadata;

import java.util.UUID;

public record VideoFileMetadataDto(
        UUID id,
        String playlistPath,
        ResolutionDto resolution,
        UUID videoId,
        Boolean isProcessed
) {
    public static VideoFileMetadataDto toVideoFileMetadataDto(VideoFileMetadata videoFileMetadata) {
        return new VideoFileMetadataDto(
                videoFileMetadata.getId(),
                videoFileMetadata.getPlayListPath(),
                ResolutionDto.toResolutionDto(videoFileMetadata.getResolution()),
                videoFileMetadata.getVideo().getId(),
                videoFileMetadata.getIsProcessed()
        );
    }
}
