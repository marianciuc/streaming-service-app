/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: KafkaProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.ResolutionResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMediaProducer {
    private KafkaTemplate<String, ResolutionResponse> kafkaTemplate;

    public void sendCreatedResolutionTopic(ResolutionResponse resolutionResponse) {
        kafkaTemplate.send("created-resolution-topic", resolutionResponse);
    }

    public void sendDeletedResolutionTopic(ResolutionResponse resolutionResponse) {
        kafkaTemplate.send("deleted-resolution-topic", resolutionResponse);
    }
}
