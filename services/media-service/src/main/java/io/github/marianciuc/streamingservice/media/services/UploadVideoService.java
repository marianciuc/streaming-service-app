/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UploadVideoService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UploadVideoService {
    void uploadVideoFile(String key, MultipartFile file);
}
