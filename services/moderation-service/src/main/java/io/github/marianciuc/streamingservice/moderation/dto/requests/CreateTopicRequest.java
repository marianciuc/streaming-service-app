/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateTopicRequest.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto.requests;

import java.util.UUID;

public record CreateTopicRequest (
        String title,
        String description,
        UUID categoryId
) {
}
