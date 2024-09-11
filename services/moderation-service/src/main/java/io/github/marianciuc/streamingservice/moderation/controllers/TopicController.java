/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicController.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.controllers;

import io.github.marianciuc.streamingservice.moderation.services.TopicMessagesService;
import io.github.marianciuc.streamingservice.moderation.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/moderation/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final TopicMessagesService topicMessagesService;

}
