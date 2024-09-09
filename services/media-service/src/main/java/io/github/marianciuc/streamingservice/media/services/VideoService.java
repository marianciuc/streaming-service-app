/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.VideoDto;
import io.github.marianciuc.streamingservice.media.enums.VideoStatues;
import io.github.marianciuc.streamingservice.media.kafka.messages.StartConvertingMessage;
import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VideoService {
    void deleteVideoByContent(UUID contentId);
    void updateMasterPlaylistUrl(String url, UUID videoId);
    VideoDto getVideoById(UUID videoId);
    VideoDto createVideoEntity(UUID contentId, Integer totalChunks, MediaType mediaType);
    void updateVideoStatus(UUID videoId, VideoStatues status);
}
