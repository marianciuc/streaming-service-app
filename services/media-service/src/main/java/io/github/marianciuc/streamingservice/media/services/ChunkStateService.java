/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChunkStateService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import java.util.UUID;

public interface ChunkStateService {
    void updateChunkUploadStatus(UUID fileId, int chunkNumber, int totalChunks);
    Boolean[] getChunkUploadStatus(UUID fileId);
    void deleteChunkUploadStatus(UUID fileId);
    void createChunkUploadStatus(UUID fileId, int totalChunks);
}
