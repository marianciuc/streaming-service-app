/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: NotesService.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services;

import io.github.marianciuc.streamingservice.moderation.dto.responses.NoteResponse;
import io.github.marianciuc.streamingservice.moderation.dto.requests.NoteRequest;
import io.github.marianciuc.streamingservice.moderation.entity.Note;

import java.util.UUID;

/**
 * Notes service as to managing topic notes.
 */
public interface NotesService {

    /**
     * Add note to topic with given id and request authenticated user details.
     * @param topicId the topic id
     * @param request the note request
     * @return the note response
     * @throws IllegalArgumentException if topic not found
     * @throws org.springframework.security.access.AccessDeniedException if user is not authorized
     */
    NoteResponse addNoteToTopic(UUID topicId, NoteRequest request);

    /**
     * Delete note with given id.
     * @param id the note id
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if note is not found
     */
    void delete(UUID id);

    /**
     * Find note by id.
     * @param id the note id
     * @return the note entity
     * @throws io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException if note is not found
     */
    Note find(UUID id);
}
