/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: NodeDto.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.dto;

import java.util.UUID;

public record NoteDto() {
    NoteDto addNoteToTopic(NoteDto noteDto) {
        return new NoteDto();
    }
}
