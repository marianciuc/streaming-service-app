/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CategoryResponse.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto.responses;

import io.github.marianciuc.streamingservice.moderation.entity.Category;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CategoryResponse toResponse(Category save) {
        return new CategoryResponse(
                save.getId(),
                save.getName(),
                save.getDescription(),
                save.getCreatedAt(),
                save.getUpdatedAt()
        );
    }
}
