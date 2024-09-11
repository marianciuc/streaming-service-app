/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TagDto.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto;

import io.github.marianciuc.streamingservice.moderation.entity.Tag;

import java.time.LocalDateTime;
import java.util.UUID;

public record TagDto(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TagDto from(Tag tag) {
        return new TagDto(tag.getId(), tag.getName(), tag.getCreatedAt(), tag.getUpdatedAt());
    }
}
