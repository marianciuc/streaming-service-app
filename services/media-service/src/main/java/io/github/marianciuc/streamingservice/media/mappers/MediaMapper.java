/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MediaMapper.java
 *
 */

package io.github.marianciuc.streamingservice.media.mappers;

import io.github.marianciuc.streamingservice.media.dto.VideoMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MediaMapper {

    private final ResolutionMapper resolutionMapper;

    public VideoMetadataDto toVideoMetadataDto(Media entity) {
        return new VideoMetadataDto(
                entity.getId(),
                resolutionMapper.toResponse(entity.getResolution()),
                entity.getContentId(),
                entity.getMediaType()
        );
    }
}
