/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResourceDto;
import io.github.marianciuc.streamingservice.media.entity.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.net.MulticastSocket;
import java.util.UUID;

public interface VideoService {
    void uploadVideo(MediaType mediaType, UUID contentId, UUID resolutionId, MultipartFile file);
    void deleteVideo(UUID id);
    ResourceDto getVideoResource(UUID videoId, HttpServletRequest request);
    void deleteVideoByContent(UUID contentId);
}
