/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: SeasonService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.SeasonDto;
import io.github.marianciuc.streamingservice.content.entity.Season;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;

import java.util.UUID;

/**
 * Service interface for managing seasons.
 */
public interface SeasonService {

    /**
     * Creates a new season by the given season DTO.
     * @param seasonDto the season DTO
     * @return the ID of the created season
     * @throws NotFoundException if the content associated with the season is not found
     */
    UUID createSeason(SeasonDto seasonDto);

    /**
     * Updates the season with the given ID by the given season DTO when the title or description is not empty.
     * @param id the ID of the season to update
     * @param seasonDto the season DTO
     * @throws NotFoundException if the season with the given ID is not found
     */
    void updateSeason(UUID id, SeasonDto seasonDto);

    /**
     * Retrieves a season by its ID.
     * @param id the ID of the season to retrieve
     * @return the SeasonDto object representing the season with the given ID
     * @throws NotFoundException if the season with the given ID is not found
     */
    SeasonDto findSeason(UUID id);

    /**
     * Retrieves a season entity by its ID.
     * @param id the ID of the season entity to retrieve
     * @return the Season object representing the season entity with the given ID
     * @throws NotFoundException if the season entity with the given ID is not found
     */
    Season findSeasonEntity(UUID id);

    /**
     * Deletes the season with the given ID. If the season has episodes, the episodes and season will be marked as deleted.
     * @param id the ID of the season to delete
     * @throws NotFoundException if the season with the given ID is not found
     */
    void deleteSeason(UUID id);


    /**
     * Retrieves a season by the content ID and season number.
     * @param contentId the ID of the content associated with the season
     * @param number the number of the season
     * @return the SeasonDto object representing the season
     * @throws NotFoundException if the season with the given content ID and number is not found
     */
    SeasonDto findSeasonByParams(UUID contentId, Integer number);
}
