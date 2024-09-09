/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoProcessingService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.kafka.messages.StartConvertingMessage;

import java.util.UUID;

public interface VideoProcessingService {
    void processMediaFile(StartConvertingMessage task);
    void startVideoProcessing(UUID id);
}
