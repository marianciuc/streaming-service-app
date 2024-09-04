/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoUploadStatusService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.enums.StatusType;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.entity.VideoUploadingStatus;
import io.github.marianciuc.streamingservice.media.repository.VideoUploadingStatusRepository;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import io.github.marianciuc.streamingservice.media.services.VideoUploadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoUploadStatusServiceImpl implements VideoUploadStatusService {

    private final VideoUploadingStatusRepository repository;
    private VideoService videoService;

    @Override
    public void createVideoUploadStatus(UUID videoId, String message, StatusType statusType, String title) {
        Video video = videoService.getVideoById(videoId);
        VideoUploadingStatus videoUploadingStatus = VideoUploadingStatus.builder()
                .type(statusType)
                .message(message)
                .title(title)
                .video(video)
                .build();
        repository.save(videoUploadingStatus);
    }
}
