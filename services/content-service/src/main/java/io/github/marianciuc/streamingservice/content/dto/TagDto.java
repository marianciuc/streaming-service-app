/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TagDto.java
 *
 */

package io.github.marianciuc.streamingservice.content.dto;

import io.github.marianciuc.streamingservice.content.entity.Tag;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TagDto(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        RecordStatus status
) {
    public static TagDto toTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName(), tag.getCreatedAt(), tag.getUpdatedAt(), tag.getRecordStatus());
    }
}
