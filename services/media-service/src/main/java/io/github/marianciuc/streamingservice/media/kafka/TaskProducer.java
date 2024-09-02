/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final KafkaTemplate<String, UUID> kafkaTemplate;

    public void sendTaskToQueue(UUID fileId) {
        return;
    }
}
