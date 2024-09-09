/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoDto.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.enums.VideoStatues;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record VideoDto (
        UUID id,
        UUID contentId,
        String contentType,
        MediaType mediaType,
        Integer processedResolutions,
        VideoStatues status,
        String masterPlaylistPath,
        Set<VideoFileMetadataDto> files,
        Set<VideoFileUploadStatusDto> statues
        ){

    public static VideoDto toVideoDto(Video video) {
        return new VideoDto(
                video.getId(),
                video.getContentId(),
                video.getContentType(),
                video.getMediaType(),
                video.getProcessedResolutions(),
                video.getStatus(),
                video.getMasterPlaylistPath(),
                video.getFiles().stream().map(VideoFileMetadataDto::toVideoFileMetadataDto).collect(Collectors.toSet()),
                video.getStatuses().stream().map(VideoFileUploadStatusDto::toVideoFileUploadStatusDto).collect(Collectors.toSet())
        );
    }
    public static Video toEntity(VideoDto videoDto) {
        return Video.builder()
                .id(videoDto.id())
                .build();
    }

}
