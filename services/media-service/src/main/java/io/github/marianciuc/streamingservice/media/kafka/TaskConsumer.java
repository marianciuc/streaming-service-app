/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskConsumer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.ConvertingTaskDto;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.github.marianciuc.streamingservice.media.kafka.KafkaTopics.START_COMPRESSING_TOPIC;


@Component
@RequiredArgsConstructor
public class TaskConsumer {

    private final VideoService videoService;

    @KafkaListener(topics = START_COMPRESSING_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeCompressTask(ConvertingTaskDto convertingTaskDto) {
        videoService.processMediaFile(convertingTaskDto);
    }
}
