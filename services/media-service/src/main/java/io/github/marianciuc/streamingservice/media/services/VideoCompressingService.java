/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FfmpegService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;

import java.io.InputStream;

public interface VideoCompressingService {
    byte [] compressVideo(InputStream file, ResolutionDto resolution);

}
