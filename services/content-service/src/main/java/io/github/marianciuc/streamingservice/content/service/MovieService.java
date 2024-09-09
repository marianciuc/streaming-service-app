/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MovieService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.kafka.messages.CreateMasterPlayListMessage;
import io.github.marianciuc.streamingservice.content.dto.MovieDto;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;

import java.util.UUID;

/**
 * Service interface for managing movies.
 */
public interface MovieService {

    /**
     * Creates a new movie by the given movie DTO with status {@code HIDDEN}
     * @param movieDto the movie DTO
     * @return the ID of the created movie
     */
    UUID createMovie(MovieDto movieDto);

    /**
     * Retrieves a movie by its ID.
     * @param movieId the ID of the movie to retrieve
     * @return the MovieDto object representing the movie with the given ID
     * @throws NotFoundException if the movie with the given ID is not found
     */
    MovieDto getMovie(UUID movieId);

    /**
     * Updates the movie master playlist link and changes status to {@code ACTIVE} if media file was uploaded successfully.
     * @param message the message containing the master playlist link and master playlist id to update
     * @throws NotFoundException if the movie with the given ID is not found
     */
    void updateMovieMasterPlaylist(CreateMasterPlayListMessage message);

    /**
     * Deletes the movie with the given ID.
     * @param movieId the ID of the movie to delete
     * @throws NotFoundException if the movie with the given ID is not found
     */
    void deleteMovie(UUID movieId);
}
