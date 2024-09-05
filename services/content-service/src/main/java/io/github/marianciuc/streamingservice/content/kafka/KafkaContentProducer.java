/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: KafkaContentProducer.java
 *
 */

package io.github.marianciuc.streamingservice.content.kafka;

import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaContentProducer {

    private KafkaTemplate<String, UUID> kafkaTemplate;

    public void sendDeleteContentMessage(UUID contentId) {
        kafkaTemplate.send("delete-content", contentId);
    }
}
