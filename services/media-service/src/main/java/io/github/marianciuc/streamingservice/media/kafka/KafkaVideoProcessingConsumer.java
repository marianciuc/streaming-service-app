/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskConsumer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.kafka.messages.StartConvertingMessage;
import io.github.marianciuc.streamingservice.media.services.VideoProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.github.marianciuc.streamingservice.media.kafka.KafkaTopics.START_COMPRESSING_TOPIC;


@Component
@RequiredArgsConstructor
public class KafkaVideoProcessingConsumer {

    private final VideoProcessingService videoProcessingService;

    @KafkaListener(topics = START_COMPRESSING_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeCompressTask(StartConvertingMessage startConvertingMessage) {
        videoProcessingService.processMediaFile(startConvertingMessage);
    }
}
