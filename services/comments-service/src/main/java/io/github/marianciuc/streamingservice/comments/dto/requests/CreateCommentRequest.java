/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateComment.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record CreateCommentRequest(
        @NotBlank(message = "Content cannot be blank")
        @Size(max = 1000, message = "Content must not exceed 1000 characters") String content,
        @NotNull(message = "Content id is required") UUID contentId
) {
}
