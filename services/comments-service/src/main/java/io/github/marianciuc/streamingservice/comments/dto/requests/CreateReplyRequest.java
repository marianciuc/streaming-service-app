/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateReplyRequest.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto.requests;

import java.util.UUID;

public record CreateReplyRequest(
        UUID parentId,
        String content
) {
}
