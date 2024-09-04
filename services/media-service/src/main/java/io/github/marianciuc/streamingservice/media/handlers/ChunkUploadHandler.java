/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChunkUploadHandler.java
 *
 */

package io.github.marianciuc.streamingservice.media.handlers;

import io.github.marianciuc.streamingservice.media.services.ChunkStateService;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChunkUploadHandler {

    private final ChunkStateService chunkStateService;
    private final VideoService videoService;

    /**
     * Handles the upload of a chunk for a file. It updates the chunk upload status and if all chunks
     * are uploaded, it processes the video.
     *
     * @param fileId      The unique identifier of the file being uploaded.
     * @param chunkNumber The sequence number of the current chunk being uploaded.
     * @param totalChunks The total number of chunks the file is divided into.
     */
    public void handleChunkUpload(UUID fileId, int chunkNumber, int totalChunks) {
        chunkStateService.updateChunkUploadStatus(fileId, chunkNumber, totalChunks);

        Boolean[] chunkStatus = chunkStateService.getChunkUploadStatus(fileId);
        if (chunkStatus != null && areAllChunksUploaded(chunkStatus)) {
            chunkStateService.deleteChunkUploadStatus(fileId);
            videoService.processVideo(fileId);
        }
    }

    /**
     * Checks whether all chunks in the provided status array have been uploaded.
     *
     * @param status An array of Boolean values representing the upload status of each chunk.
     *               A value of {@code true} indicates the chunk has been uploaded,
     *               {@code false} indicates it has not been uploaded,
     *               and {@code null} indicates unknown status.
     * @return {@code true} if all chunks are marked as uploaded; {@code false} otherwise.
     */
    private boolean areAllChunksUploaded(Boolean[] status) {
        for (Boolean s : status) {
            if (s == null || !s) {
                return false;
            }
        }
        return true;
    }
}
