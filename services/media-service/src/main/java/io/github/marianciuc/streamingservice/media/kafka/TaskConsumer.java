/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskConsumer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.ConvertingTaskDto;
import io.github.marianciuc.streamingservice.media.dto.MediaDeleteMessage;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class TaskConsumer {

    private VideoService videoService;

    @KafkaListener(topics = "video-converting-topic", groupId = "${spring.kafka.consumer.group-id}")
    private void consumeCompressTask(MediaDeleteMessage mediaDto) {
        videoService.deleteVideoByContent(mediaDto.contentId());
    }

    @KafkaListener(topics = "video-converting-topic", groupId = "${spring.kafka.consumer.group-id}")
    private void consumeCompressTask(ConvertingTaskDto convertingTaskDto) {
        videoService.processMediaFile(convertingTaskDto);
    }
}
