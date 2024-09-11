/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: NotesService.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.dto.NoteDto;

import java.util.UUID;

public interface NotesService {
    NoteDto addNoteToTopic(NoteDto noteDto);
    void deleteNote(UUID noteId);
    NoteDto findNoteById(UUID noteId);
}
