/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ModerationMessage.java
 *
 */

package io.github.marianciuc.streamingservice.comments.kafka.messages;

import io.github.marianciuc.streamingservice.comments.entity.ModerationStatus;

import java.util.UUID;

public record ModerationMessage(
        UUID commentId,
        ModerationStatus moderationStatus
) {
}
