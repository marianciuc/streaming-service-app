/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicMessagesService.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.dto.TopicMessageDto;
import io.github.marianciuc.streamingservice.moderation.entity.TopicMessage;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

/**
 * Service for managing messages in the topic
 */
public interface TopicMessagesService {

    /**
     * Add message to the topic
     * @param message - message to be added to the topic
     * @throws IllegalArgumentException if the topic is closed
     * @throws AccessDeniedException if user does not have permission to add message to the topic
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if topic is not found
     */
    void addMessageToTopic(TopicMessageDto message);

    /**
     * Delete message with given id
     * @param messageId - id of the message to be deleted
     * @throws AccessDeniedException if user does not have permission to delete the message
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if message is not found
     */
    void delete(UUID messageId);

    /**
     * Find message by id
     * @param id - id of the message to be found
     * @return the message entity
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if message is not found
     */
    TopicMessage find(UUID id) ;
}
