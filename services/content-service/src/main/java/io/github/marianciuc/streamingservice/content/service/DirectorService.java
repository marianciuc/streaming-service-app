/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PersonService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.PersonDto;
import io.github.marianciuc.streamingservice.content.entity.Director;

import java.util.UUID;

public interface DirectorService {
    UUID createDirector(PersonDto personDto);
    void deleteDirector(UUID id);
    void updateDirector(UUID id, PersonDto personDto);
    PersonDto findDirectorById(UUID id);
    Director findDirectorEntityById(UUID id);
}
