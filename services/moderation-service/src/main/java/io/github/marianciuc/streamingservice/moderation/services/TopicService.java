/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicService.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.dto.requests.CreateTopicRequest;
import io.github.marianciuc.streamingservice.moderation.dto.TopicDto;
import io.github.marianciuc.streamingservice.moderation.entity.Topic;
import io.github.marianciuc.streamingservice.moderation.entity.TopicStatus;

import java.util.List;
import java.util.UUID;

public interface TopicService {
    UUID create(CreateTopicRequest createTopicRequest);
    void updateStatus(UUID topicId, TopicStatus topicStatus);
    Topic findTopic(UUID topicId);
    List<TopicDto> findAllTopicsByFilters(UUID userId, TopicStatus topicStatus, Integer page, Integer size);
}