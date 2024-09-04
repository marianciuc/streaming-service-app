/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.services.ResolutionService;
import io.github.marianciuc.streamingservice.media.services.VideoCompressingService;
import io.github.marianciuc.streamingservice.media.services.VideoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final KafkaTemplate<String, UUID> kafkaTemplate;
    private final VideoStorageService videoStorageService;
    private final VideoCompressingService videoCompressingService;
    private final ResolutionService resolutionService;

    public void sendTaskToQueue(UUID fileId, int chunkNumber, int totalChunks, UUID contentId, UUID sourceResolutionId) {
        // поставить статус фильм в обработке
        try (InputStream is = videoStorageService.assembleVideo(fileId)) {
            List<ResolutionDto> resolutions = resolutionService.getAllResolutions().stream().filter((resolutionDto -> resolutionDto.height() < sourceResolution.height())).toList();

            for (ResolutionDto resolution : resolutions) {
                videoCompressingService.processVideo(is, resolution);
            }
            String masterPlaylistPath = videoStorageService.createMasterPlaylist(fileId, resolutions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
