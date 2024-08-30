/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: KafkaProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.ResolutionMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static io.github.marianciuc.streamingservice.media.kafka.KafkaTopics.*;

@Component
public class KafkaResolutionProducer {

    private KafkaTemplate<String, ResolutionMessage> kafkaTemplate;

    public void sendCreatedResolutionTopic(ResolutionMessage resolutionResponse) {
        kafkaTemplate.send(RESOLUTION_CREATED_TOPIC, resolutionResponse);
    }

    public void sendUpdateResolutionTopic(ResolutionMessage resolutionResponse) {
        kafkaTemplate.send(RESOLUTION_UPDATED_TOPIC, resolutionResponse);
    }

    public void sendDeleteResolutionTopic(ResolutionMessage resolutionResponse) {
        kafkaTemplate.send(RESOLUTION_DELETED_TOPIC, resolutionResponse);
    }
}
