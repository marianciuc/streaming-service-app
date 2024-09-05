/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: KafkaMediaProducer.java
 *
 */

package io.github.marianciuc.streamingservice.content.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaMediaProducer {

    private KafkaTemplate<String, UUID> kafkaTemplate;

    public void sendDeleteMediaMessage(UUID movieId) {
        kafkaTemplate.send("delete-movie", movieId);
    }
}
