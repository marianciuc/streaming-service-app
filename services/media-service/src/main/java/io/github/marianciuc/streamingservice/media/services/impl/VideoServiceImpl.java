/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.services.ChunkStateService;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final ChunkStateService chunkStateService;

    @Override
    public void uploadVideo(MediaType mediaType, UUID contentId, UUID resolutionId, MultipartFile file) {

    }

    @Override
    public void deleteVideo(UUID id) {

    }

    @Override
    public void deleteVideoByContent(UUID contentId) {

    }

    @Override
    public void processVideo(UUID videoId) {

    }
}
