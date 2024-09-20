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

import java.util.UUID;

/**
 * Interface representing a video compression service.
 */
public interface VideoCompressingService {

    /**
     * This method compresses the video with the specified resolution and uploads it to the storage in chunks. The
     * method returns the playlist path of the compressed video in the storage.
     *
     * @param resolution the resolution to compress and upload video
     * @param id the id of the video to compress and upload
     * @return the playlist path of the compressed video in the storage
     * @throws CompressingException if the video compression fails
     */
    String compressVideoAndUploadToStorage(ResolutionDto resolution, UUID id) throws CompressingException;
}
