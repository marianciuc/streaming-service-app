/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services.impl;

import io.github.marianciuc.streamingservice.moderation.dto.requests.CreateTopicRequest;
import io.github.marianciuc.streamingservice.moderation.dto.TopicDto;
import io.github.marianciuc.streamingservice.moderation.entity.Category;
import io.github.marianciuc.streamingservice.moderation.entity.Topic;
import io.github.marianciuc.streamingservice.moderation.entity.TopicStatus;
import io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.moderation.repositories.TopicRepository;
import io.github.marianciuc.streamingservice.moderation.security.services.UserService;
import io.github.marianciuc.streamingservice.moderation.services.CategoryService;
import io.github.marianciuc.streamingservice.moderation.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public UUID create(CreateTopicRequest createTopicRequest) {
        Category category = categoryService.find(createTopicRequest.categoryId());

        Topic topic = Topic.builder()
                .title(createTopicRequest.title())
                .description(createTopicRequest.description())
                .userId(userService.extractUserIdFromAuth())
                .status(TopicStatus.OPEN)
                .category(category)
                .build();

        // TODO send message to Kafka
        return repository.save(topic).getId();
    }

    @Override
    public void updateStatus(UUID topicId, TopicStatus topicStatus) {
        Topic topic = this.findTopic(topicId);
        topic.setStatus(topicStatus);
        repository.save(topic);
    }

    @Override
    public Topic findTopic(UUID topicId) {
        return this.repository.findById(topicId).orElseThrow(() -> new NotFoundException("Topic not found"));
    }

    @Override
    public List<TopicDto> findAllTopicsByFilters(UUID userId, TopicStatus topicStatus, Integer page, Integer size) {
        return List.of();
    }
}
