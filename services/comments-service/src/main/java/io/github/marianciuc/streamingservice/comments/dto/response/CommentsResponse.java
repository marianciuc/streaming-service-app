/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CommentsResponse.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto.response;

import java.util.List;
import java.util.UUID;

public record CommentsResponse(
        UUID id,
        String content,
        List<CommentsResponse> replies,
        UUID userId,
        String username,
        String profilePicture
) {
}
