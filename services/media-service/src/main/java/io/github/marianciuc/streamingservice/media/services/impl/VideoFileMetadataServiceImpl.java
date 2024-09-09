/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoFileMetadataServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.dto.VideoDto;
import io.github.marianciuc.streamingservice.media.dto.VideoFileMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.VideoFileMetadata;
import io.github.marianciuc.streamingservice.media.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.media.repository.VideoFileMetadataRepository;
import io.github.marianciuc.streamingservice.media.services.VideoFileMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class VideoFileMetadataServiceImpl implements VideoFileMetadataService {

    private final VideoFileMetadataRepository repository;

    @Override
    public VideoFileMetadataDto createMetadata(VideoDto video, ResolutionDto resolution) {
        VideoFileMetadata metadata = VideoFileMetadata.builder()
                .video(VideoDto.toEntity(video))
                .playListPath("")
                .isProcessed(false)
                .resolution(ResolutionDto.toEntity(resolution))
                .build();
        return VideoFileMetadataDto.toVideoFileMetadataDto(repository.save(metadata));
    }

    @Override
    public void updateMetadata(UUID id, Boolean status, String playlistPath) {
        VideoFileMetadata metadata = repository.findById(id).orElseThrow(() -> new NotFoundException("Metadata not found"));
        if (status != null) metadata.setIsProcessed(status);
        if (!playlistPath.isEmpty()) metadata.setPlayListPath(playlistPath);
    }

    @Override
    public void deleteMetadata() {

    }
}
