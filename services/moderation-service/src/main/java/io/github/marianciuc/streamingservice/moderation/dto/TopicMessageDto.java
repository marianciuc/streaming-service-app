/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicMessageDto.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto;

import java.util.UUID;

public record TopicMessageDto(
        UUID id,
        String content,
        UUID topicId
) {
}
