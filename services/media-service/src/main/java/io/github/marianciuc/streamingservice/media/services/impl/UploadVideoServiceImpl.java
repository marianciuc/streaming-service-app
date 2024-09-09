/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UploadVideoService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.dto.VideoDto;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.kafka.KafkaPlaylistProducer;
import io.github.marianciuc.streamingservice.media.kafka.messages.MasterPlaylistMessage;
import io.github.marianciuc.streamingservice.media.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UploadVideoServiceImpl implements UploadVideoService {

    private static final long CHUNK_SIZE = 10 * 1024 * 1024;

    private final VideoStorageService videoStorage;
    private final VideoService videoService;
    private final ChunkStateService chunkStateService;
    private final KafkaPlaylistProducer kafkaPlaylistProducer;
    private final VideoProcessingService videoProcessingService;

    @Override
    public void uploadVideo(MultipartFile chunk, Integer chunkNumber, UUID id, Integer totalChunks) {
        this.videoStorage.uploadVideoChunk(chunk, chunkNumber, id);
        this.handleChunkUpload(id, chunkNumber, totalChunks);
    }

    @Override
    public UploadMetadataDto prepareVideo(String contentType, long fileSize, MediaType mediaType, UUID contentId) {
        int totalChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);
        VideoDto videoDto = this.videoService.createVideoEntity(contentId, totalChunks, mediaType);

        this.chunkStateService.createChunkUploadStatus(videoDto.id(), totalChunks);
        this.kafkaPlaylistProducer.sendMasterPlaylistCreated(new MasterPlaylistMessage(
                contentId, mediaType, videoDto.id(), ""));

        return new UploadMetadataDto(videoDto.id(), totalChunks);
    }

    private void handleChunkUpload(UUID fileId, int chunkNumber, int totalChunks) {
        this.chunkStateService.updateChunkUploadStatus(fileId, chunkNumber, totalChunks);

        Boolean[] chunkStatus = chunkStateService.getChunkUploadStatus(fileId);

        if (chunkStatus != null && areAllChunksUploaded(chunkStatus)) {
            chunkStateService.deleteChunkUploadStatus(fileId);
            videoProcessingService.startVideoProcessing(fileId);
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
