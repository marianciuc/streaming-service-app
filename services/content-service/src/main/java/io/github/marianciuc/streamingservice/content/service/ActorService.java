/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ActorService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.PersonDto;
import io.github.marianciuc.streamingservice.content.entity.Actor;

import java.util.UUID;

public interface ActorService {

    UUID createActor(PersonDto personDto);

    void deleteActor(UUID id);

    void updateActor(UUID id, PersonDto personDto);

    PersonDto findActorById(UUID id);

    Actor findActorEntityById(UUID id);
}
