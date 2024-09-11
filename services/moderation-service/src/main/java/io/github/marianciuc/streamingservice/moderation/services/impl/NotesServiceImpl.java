/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: NotesServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services.impl;

import io.github.marianciuc.streamingservice.moderation.dto.NoteDto;
import io.github.marianciuc.streamingservice.moderation.repositories.NotesRepository;
import io.github.marianciuc.streamingservice.moderation.services.NotesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotesServiceImpl implements NotesService {

    private final NotesRepository repository;

    @Override
    public NoteDto addNoteToTopic(NoteDto noteDto) {
        return null;
    }

    @Override
    public void deleteNote(UUID noteId) {

    }

    @Override
    public NoteDto findNoteById(UUID noteId) {
        return null;
    }
}
