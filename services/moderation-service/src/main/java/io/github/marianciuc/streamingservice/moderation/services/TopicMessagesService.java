/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicMessagesService.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.dto.TopicDto;
import io.github.marianciuc.streamingservice.moderation.dto.TopicMessageDto;

public interface TopicMessagesService {
    void addMessageToTopic(TopicMessageDto message);
    void delete(String messageId);
    TopicDto find(String id);

}
