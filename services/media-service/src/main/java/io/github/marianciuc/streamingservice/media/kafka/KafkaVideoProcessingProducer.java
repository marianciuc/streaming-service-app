/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TaskProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.kafka.messages.StartConvertingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.github.marianciuc.streamingservice.media.kafka.KafkaTopics.START_COMPRESSING_TOPIC;

@Service
@RequiredArgsConstructor
public class KafkaVideoProcessingProducer {

    private final KafkaTemplate<String, StartConvertingMessage> kafkaTemplate;

    public void sendTaskToQueue(StartConvertingMessage startConvertingMessage) {
        Message<StartConvertingMessage> message = MessageBuilder
                .withPayload(startConvertingMessage)
                .setHeader(KafkaHeaders.TOPIC, START_COMPRESSING_TOPIC)
                .build();
        kafkaTemplate.send(message);
    }
}
