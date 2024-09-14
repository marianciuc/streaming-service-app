/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: NotesServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.services.impl;

import io.github.marianciuc.streamingservice.moderation.dto.responses.NoteResponse;
import io.github.marianciuc.streamingservice.moderation.dto.requests.NoteRequest;
import io.github.marianciuc.streamingservice.moderation.entity.Note;
import io.github.marianciuc.streamingservice.moderation.entity.Topic;
import io.github.marianciuc.streamingservice.moderation.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.moderation.repositories.NotesRepository;
import io.github.marianciuc.streamingservice.moderation.security.services.UserService;
import io.github.marianciuc.streamingservice.moderation.services.NotesService;
import io.github.marianciuc.streamingservice.moderation.services.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotesServiceImpl implements NotesService {

    private final NotesRepository repository;
    private final TopicService topicService;
    private final UserService userService;

    @Override
    public NoteResponse addNoteToTopic(UUID topicId, NoteRequest request) {
        Topic topic = topicService.findTopic(topicId);
        Note note = Note.builder()
                .topic(topic)
                .authorId(userService.extractUserIdFromAuth())
                .note(request.content())
                .build();
        return NoteResponse.toResponse(repository.save(note));
    }

    @Override
    public void delete(UUID id) {
        Note note = this.find(id);
        this.repository.delete(note);
    }

    @Override
    public Note find(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Note not found"));
    }
}
