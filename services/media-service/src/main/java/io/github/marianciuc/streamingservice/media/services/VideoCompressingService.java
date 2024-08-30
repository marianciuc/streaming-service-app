/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FfmpegService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import org.springframework.web.multipart.MultipartFile;

public interface VideoCompressingService {
    byte [] compressVideo(MultipartFile file, Resolution resolution);
}
