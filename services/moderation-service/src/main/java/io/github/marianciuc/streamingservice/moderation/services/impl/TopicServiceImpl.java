/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services.impl;

import io.github.marianciuc.streamingservice.moderation.dto.CreateTopicRequest;
import io.github.marianciuc.streamingservice.moderation.dto.TopicDto;
import io.github.marianciuc.streamingservice.moderation.entity.Topic;
import io.github.marianciuc.streamingservice.moderation.entity.TopicStatus;
import io.github.marianciuc.streamingservice.moderation.repositories.TopicRepository;
import io.github.marianciuc.streamingservice.moderation.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository repository;

    @Override
    public UUID createTopic(CreateTopicRequest createTopicRequest) {
        return null;
    }

    @Override
    public void changeTopicStatus(UUID topicId, TopicStatus topicStatus) {

    }

    @Override
    public Topic findTopicEntityById(UUID topicId) {
        return null;
    }

    @Override
    public List<TopicDto> findAllTopicsByFilters(UUID userId, TopicStatus topicStatus, Integer page, Integer size) {
        return List.of();
    }
}
