/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: KafkaPlaylistProducer.java
 *
 */

package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.kafka.messages.MasterPlaylistMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPlaylistProducer {

    private final KafkaTemplate<String, MasterPlaylistMessage> kafkaTemplate;

    public void sendMasterPlaylistCreated(MasterPlaylistMessage masterPlaylistMessage) {
        kafkaTemplate.send("master-playlist-update-topic", masterPlaylistMessage);
    }
}
