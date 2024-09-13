/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateComment.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto.requests;

import java.util.UUID;

public record CreateCommentRequest(
        String content,
        UUID contentId
) {
}
