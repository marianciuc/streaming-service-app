/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateReplyRequest.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateReplyRequest(
        @NotNull(message = "Parent ID cannot be null")
        UUID parentId,
        @NotBlank(message = "Content cannot be blank")
        @Size(max = 1000, message = "Content must not exceed 1000 characters") String content
) {
}
