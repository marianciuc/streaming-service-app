/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: NoteResponse.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto.responses;

import io.github.marianciuc.streamingservice.moderation.entity.Note;

import java.time.LocalDateTime;
import java.util.UUID;

public record NoteResponse(
        UUID id,
        String content,
        UUID authorId,
        LocalDateTime createdAt
) {
    public static NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getNote(),
                note.getAuthorId(),
                note.getCreatedAt()
        );
    }
}
