/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChunkStateService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.exceptions.ChunkUploadNotInitializedException;
import io.github.marianciuc.streamingservice.media.exceptions.ChunkUploadTimeoutException;

import java.util.UUID;

/**
 * Service for managing the chunk upload status for a video file uploading.
 */
public interface ChunkStateService {

    /**
     * Update the chunk upload status for a video file. To use this method, previously u must call
     * createChunkUploadStatus to initialize the chunk upload status.
     * @param fileId video id
     * @param chunkNumber present chunk number
     * @param totalChunks total number of chunks
     * @throws ChunkUploadNotInitializedException if the chunk upload status is not initialized
     * @throws ChunkUploadTimeoutException if the chunk number exceeds total chunks
     */
    void updateChunkUploadStatus(UUID fileId, int chunkNumber, int totalChunks);


    /**
     * Delete the chunk upload status for a video file.
     * @param fileId video id
     */
    void deleteChunkUploadStatus(UUID fileId);

    /**
     * Create the chunk upload status for a video file. If the status already exists, it will be overwritten.
     * @param fileId video id
     * @param totalChunks total number of chunks
     */
    void createChunkUploadStatus(UUID fileId, int totalChunks);

    /**
     * Check if the upload is complete for a video file.
     * @param fileId video id
     * @return true if the upload is complete, false otherwise
     */
    boolean isUploadComplete(UUID fileId);
}
