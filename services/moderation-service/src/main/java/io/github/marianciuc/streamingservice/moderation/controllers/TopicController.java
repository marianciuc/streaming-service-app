/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicController.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.controllers;

import io.github.marianciuc.streamingservice.moderation.dto.CreateTopicRequest;
import io.github.marianciuc.streamingservice.moderation.services.TopicMessagesService;
import io.github.marianciuc.streamingservice.moderation.services.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/v1/moderation/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final TopicMessagesService topicMessagesService;

    @PostMapping
    public ResponseEntity<UUID> createTopic(@Valid @RequestBody CreateTopicRequest createTopicRequest) {
        return ResponseEntity.ok(topicService.createTopic(createTopicRequest));
    }



}
