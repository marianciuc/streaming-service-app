/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoUploadStatusService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.VideoDto;
import io.github.marianciuc.streamingservice.media.dto.VideoFileUploadStatusDto;
import io.github.marianciuc.streamingservice.media.enums.StatusType;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.entity.VideoUploadingStatus;
import io.github.marianciuc.streamingservice.media.repository.VideoUploadingStatusRepository;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import io.github.marianciuc.streamingservice.media.services.VideoUploadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the VideoUploadStatusService interface.
 * This service is responsible for managing the upload statuses of videos.
 * It provides methods to create a new upload status entry for a video.
 */
@Service
@RequiredArgsConstructor
public class VideoUploadStatusServiceImpl implements VideoUploadStatusService {

    private final VideoUploadingStatusRepository repository;
    private final VideoService videoService;

    /**
     * Creates a new status entry for a video upload process.
     *
     * @param videoId the unique identifier of the video
     * @param message the status message associated with the video upload
     * @param statusType the type of status, which can be INFO, WARNING, or ERROR
     * @param title the title of the status entry
     * @throws io.github.marianciuc.streamingservice.media.exceptions.NotFoundException if the video with the given ID does not exist
     */
    @Override
    public void createVideoUploadStatus(UUID videoId, String message, StatusType statusType, String title) {
        VideoDto video = videoService.getVideoById(videoId);
        VideoUploadingStatus videoUploadingStatus = VideoUploadingStatus.builder()
                .type(statusType)
                .message(message)
                .title(title)
                .video(VideoDto.toEntity(video))
                .build();
        repository.save(videoUploadingStatus);
    }

    @Override
    public List<VideoFileUploadStatusDto> getVideoUploadStatus(UUID videoId) {
        return repository.findAllByVideoId(videoId).stream().map(VideoFileUploadStatusDto::toVideoFileUploadStatusDto).toList();
    }
}
