/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateTopicRequest.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto;

public record CreateTopicRequest (
        String title,
        String description
) {
}
