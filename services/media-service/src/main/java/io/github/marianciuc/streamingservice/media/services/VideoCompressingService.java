/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: FfmpegService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.exceptions.CompressingException;

import java.io.InputStream;
import java.util.UUID;

/**
 * Interface representing a video compression service.
 */
public interface VideoCompressingService {
    /**
     * Compresses the input video stream to the specified resolution and uploads it to the storage.
     *
     * @param io the input stream of the video to be compressed
     * @param resolution the target resolution for the video compression
     * @param id the unique identifier of the video
     * @return the storage location of the uploaded compressed video
     * @throws CompressingException if an error occurs during the video compression process
     */
    String compressVideoAndUploadToStorage(InputStream io, ResolutionDto resolution, UUID id) throws CompressingException;
}
