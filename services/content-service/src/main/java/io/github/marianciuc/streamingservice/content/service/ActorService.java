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
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;

import java.util.UUID;

/**
 * The service interface for the actor entity
 */
public interface ActorService {

    /**
     * Creates an actor with the given data transfer object
     *
     * @param personDto - the person data transfer object
     * @return the UUID of the created actor
     */
    UUID createActor(PersonDto personDto);

    /**
     * Deletes an actor by the given UUID from the database
     *
     * @param id - the UUID of the actor to be deleted
     */
    void deleteActor(UUID id);


    /**
     * Updates an actor with the given data transfer object where fields are not null
     *
     * @param id        - the UUID of the actor to be updated
     * @param personDto - the person data transfer object
     */
    void updateActor(UUID id, PersonDto personDto);


    /**
     * Finds an actor by the given UUID and returns the person data transfer object
     *
     * @param id - the UUID of the actor to be retrieved
     * @return the person data transfer object of the actor with the given UUID
     * @throws NotFoundException if the actor with the given UUID is not found
     */
    PersonDto findActorById(UUID id);

    /**
     * Finds an actor by the given UUID and returns the actor entity
     * @param id the UUID of the actor to be retrieved
     * @return the actor entity with the given UUID
     * @throws NotFoundException if the actor with the given UUID is not found
     */
    Actor findActorEntityById(UUID id);
}
