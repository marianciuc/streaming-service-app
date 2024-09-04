/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoStorageService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface VideoStorageService {
    void uploadVideoChunk(MultipartFile chunk, Integer chunkNumber, UUID fileId);
    void deleteVideo(UUID fileId);
    InputStream assembleVideo(UUID fileId);
}
