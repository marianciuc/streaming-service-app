/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MessageModerationServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services.impl;

import io.github.marianciuc.streamingservice.moderation.kafka.messages.CreatedNewMessage;
import io.github.marianciuc.streamingservice.moderation.repositories.ModerationMessageRepository;
import io.github.marianciuc.streamingservice.moderation.services.MessageModerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageModerationServiceImpl implements MessageModerationService {

    private final ModerationMessageRepository repository;

    @Override
    public void startModeration(CreatedNewMessage message) {
    }

    @Override
    public void rejectMessage(String reason, UUID id) {

    }

    @Override
    public void approveMessage(UUID id) {

    }
}
