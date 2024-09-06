/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: GenreService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.GenreDto;
import io.github.marianciuc.streamingservice.content.entity.Genre;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing genres.
 */
public interface GenreService {


    /**
     * Creates a new genre by the given genre DTO.
     * @param genreDto the genre DTO
     * @return the ID of the created genre
     */
    UUID createGenre(GenreDto genreDto);


    /**
     * Retrieves a genre by its ID.
     * @param id the ID of the genre to retrieve
     * @return the GenreDto object representing the genre
     * @throws NotFoundException if the genre with the given ID is not found
     */
    GenreDto getGenre(UUID id);


    /**
     * Updates the genre with the given ID by the given genre DTO. Updates only the fields that are not null.
     * @param id the ID of the genre to update
     * @param genreDto the genre DTO
     * @throws NotFoundException if the genre with the given ID is not found
     */
    void updateGenre(UUID id, GenreDto genreDto);


    /**
     * Deletes the genre with the given ID.
     * @param id the id of the genre to delete
     * @throws NotFoundException if the genre with the given ID is not found
     */
    void deleteGenre(UUID id);


    /**
     * Retrieves a genre entity by its ID.
     * @param id the ID of the genre entity to retrieve
     * @return the Genre object representing the genre entity with the given ID
     * @throws NotFoundException if the genre entity with the given ID is not found
     */
    Genre getGenreEntity(UUID id);


    /**
     * @return the list of all genres or an empty list if no genres are found
     */
    List<GenreDto> getAllGenres();
}
