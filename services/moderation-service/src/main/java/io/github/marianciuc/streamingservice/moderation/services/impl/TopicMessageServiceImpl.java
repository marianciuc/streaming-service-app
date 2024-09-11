/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicMessageServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services.impl;

import io.github.marianciuc.streamingservice.moderation.dto.TopicDto;
import io.github.marianciuc.streamingservice.moderation.dto.TopicMessageDto;
import io.github.marianciuc.streamingservice.moderation.repositories.TopicMessageRepository;
import io.github.marianciuc.streamingservice.moderation.services.TopicMessagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicMessageServiceImpl implements TopicMessagesService {

    private final TopicMessageRepository repository;

    @Override
    public void addMessageToTopic(TopicMessageDto message) {

    }

    @Override
    public void delete(String messageId) {

    }

    @Override
    public TopicDto find(String id) {
        return null;
    }
}
