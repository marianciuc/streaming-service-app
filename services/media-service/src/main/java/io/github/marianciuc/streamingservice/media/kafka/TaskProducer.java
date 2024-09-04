/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.ConvertingTaskDto;
import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final KafkaTemplate<String, ConvertingTaskDto> kafkaTemplate;

    public void sendTaskToQueue(UUID fileId, ResolutionDto resolutionDto) {
        kafkaTemplate.send("video-converting-topic",
                new ConvertingTaskDto(fileId, resolutionDto));
    }
}
