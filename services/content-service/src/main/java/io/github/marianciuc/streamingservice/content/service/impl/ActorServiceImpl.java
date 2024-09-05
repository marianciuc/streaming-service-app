/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ActorServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.PersonDto;
import io.github.marianciuc.streamingservice.content.entity.Actor;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.repository.ActorRepository;
import io.github.marianciuc.streamingservice.content.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository repository;

    @Override
    public UUID createActor(PersonDto personDto) {
        Actor actor = Actor.builder()
                .firstName(personDto.firstName())
                .lastName(personDto.lastName())
                .biography(personDto.biography())
                .birthPlace(personDto.birthPlace())
                .birthDate(personDto.birthDate())
                .build();
        return repository.save(actor).getId();
    }

    @Override
    public void deleteActor(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Actor not found");
        }
        repository.deleteById(id);
    }

    @Override
    public void updateActor(UUID id, PersonDto personDto) {
        Actor director = findActorEntityById(id);
        if (personDto.firstName() != null) director.setFirstName(personDto.firstName());
        if (personDto.lastName() != null) director.setLastName(personDto.lastName());
        if (personDto.biography() != null) director.setBiography(personDto.biography());
        if (personDto.birthPlace() != null) director.setBirthPlace(personDto.birthPlace());
        if (personDto.birthDate() != null) director.setBirthDate(personDto.birthDate());
        repository.save(director);
    }

    @Override
    public PersonDto findActorById(UUID id) {
        return repository.findById(id)
                .map(PersonDto::toPersonDto)
                .orElseThrow(() -> new NotFoundException("Actor not found"));
    }

    @Override
    public Actor findActorEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Actor not found"));
    }
}
