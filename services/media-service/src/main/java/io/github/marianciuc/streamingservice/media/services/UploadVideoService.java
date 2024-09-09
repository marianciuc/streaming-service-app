/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UploadVideoService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UploadVideoService {
    void uploadVideo(MultipartFile chunk, Integer chunkNumber, UUID id, Integer totalChunks);
    UploadMetadataDto prepareVideo(String contentType, long fileSize, MediaType mediaType, UUID contentId);
}
