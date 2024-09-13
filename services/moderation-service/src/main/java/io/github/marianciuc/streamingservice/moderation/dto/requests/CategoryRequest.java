/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateCategoryRequest.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto.requests;

public record CategoryRequest(
        String name,
        String description
) {
}