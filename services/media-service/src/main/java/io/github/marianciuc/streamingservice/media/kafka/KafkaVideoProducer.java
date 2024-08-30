/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: KafkaProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.VideoMetadataDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static io.github.marianciuc.streamingservice.media.kafka.KafkaTopics.*;

@Component
public class KafkaVideoProducer {

    private KafkaTemplate<String, VideoMetadataDto> kafkaTemplate;

    public void sendUploadedVideoTopic(VideoMetadataDto videoMetadataDto) {
        kafkaTemplate.send(RESOLUTION_CREATED_TOPIC, videoMetadataDto);
    }

    public void sendDeletedVideoTopic(VideoMetadataDto videoMetadataDto) {
        kafkaTemplate.send(RESOLUTION_CREATED_TOPIC, videoMetadataDto);
    }
}
