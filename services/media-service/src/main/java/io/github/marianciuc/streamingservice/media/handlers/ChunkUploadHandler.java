/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChunkUploadHandler.java
 *
 */

package io.github.marianciuc.streamingservice.media.handlers;

import io.github.marianciuc.streamingservice.media.kafka.TaskProducer;
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


    public void handleChunkUpload(UUID fileId, int chunkNumber, int totalChunks) {
        chunkStateService.updateChunkUploadStatus(fileId, chunkNumber, totalChunks);

        Boolean[] chunkStatus = chunkStateService.getChunkUploadStatus(fileId);
        if (chunkStatus != null && areAllChunksUploaded(chunkStatus)) {
            chunkStateService.deleteChunkUploadStatus(fileId);
            videoService.processVideo(fileId);
        }
    }

    private boolean areAllChunksUploaded(Boolean[] status) {
        for (Boolean s : status) {
            if (s == null || !s) {
                return false;
            }
        }
        return true;
    }
}
