/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VideoService {
    void uploadVideo(MediaType mediaType, UUID contentId, UUID resolutionId, MultipartFile file);
    void deleteVideo(UUID id);
    void deleteVideoByContent(UUID contentId);
    void processVideo(UUID videoId);
    UploadMetadataDto prepareVideo(String contentType, long fileSize, MediaType mediaType, UUID contentId);
    Video getVideoById(UUID videoId);
}
