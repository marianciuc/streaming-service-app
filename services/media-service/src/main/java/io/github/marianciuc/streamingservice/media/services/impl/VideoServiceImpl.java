/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.VideoDto;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.enums.VideoStatues;
import io.github.marianciuc.streamingservice.media.exceptions.ImageNotFoundException;
import io.github.marianciuc.streamingservice.media.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.media.repository.VideoRepository;
import io.github.marianciuc.streamingservice.media.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for handling video-related operations.
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private static final String DEFAULT_CONTENT_TYPE = "video/mp4";
    private final VideoRepository repository;

    public void deleteVideoByContent(UUID contentId) {

    }

    @Override
    public void updateMasterPlaylistUrl(String url, UUID videoId) {
        Video video = this.findVideoById(videoId);
        video.setMasterPlaylistPath(url);
        repository.save(video);
    }

    public VideoDto createVideoEntity(UUID contentId, Integer totalChunks, MediaType mediaType) {
        Video video = Video.builder()
                .status(VideoStatues.PREPARED)
                .contentType(DEFAULT_CONTENT_TYPE)
                .mediaType(mediaType)
                .contentId(contentId)
                .build();
        return VideoDto.toVideoDto(repository.save(video));
    }

    @Override
    public void updateVideoStatus(UUID videoId, VideoStatues status) {
        Video video = this.findVideoById(videoId);
        video.setStatus(status);
        repository.save(video);
    }

    private Video findVideoById(UUID id) {
        return repository.findVideoById(id).orElseThrow(() -> new NotFoundException("Video not found"));
    }

    @Override
    public VideoDto getVideoById(UUID videoId) {
        return repository.findVideoById(videoId).map(VideoDto::toVideoDto).orElseThrow(() -> new ImageNotFoundException(
                "Video " +
                "not found"));
    }
}