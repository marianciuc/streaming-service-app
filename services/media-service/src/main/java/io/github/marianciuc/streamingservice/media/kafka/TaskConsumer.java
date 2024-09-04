/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskConsumer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.MediaDeleteMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class TaskConsumer {

    @KafkaListener(topics = "video-converting-topic", groupId = "${spring.kafka.consumer.group-id}")
    private void consumeCompressTask(MediaDeleteMessage mediaDto) {
        videoService.deleteVideoByContent(mediaDto.contentId());
    }
}
