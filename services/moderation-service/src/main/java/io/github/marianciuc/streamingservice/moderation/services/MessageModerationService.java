/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MessageModerationService.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.kafka.messages.CreatedNewMessage;

import java.util.UUID;

public interface MessageModerationService {
    void startModeration(CreatedNewMessage message);
    void rejectMessage(String reason, UUID id);
    void approveMessage(UUID id);
}
