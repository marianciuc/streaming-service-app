/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing resolutions.
 */
public interface ResolutionService {

    /**
     * Creates a new resolution.
     * @param request resolution dto to create
     * @return the created resolution dto
     */
    ResolutionDto createResolution(ResolutionDto request);

    /**
     * Updates the resolution.
     * @param request resolution dto to update
     * @return the updated resolution dto
     */
    ResolutionDto updateResolution(ResolutionDto request);

    /**
     * Gets all resolutions DTOs.
     * @return the list of all resolutions or an empty list if there are no resolutions
     */
    List<ResolutionDto> getAllResolutions();

    /**
     * Gets the resolution by id.
     * @param id the id of the resolution
     * @return the resolution dto
     * @throws IllegalArgumentException if the resolution with the given id does not exist
     */
    ResolutionDto getResolutionById(UUID id);

    /**
     * Deletes the resolution by id form database.
     * @param id the id of the resolution to delete
     * @throws IllegalArgumentException if the resolution with the given id does not exist
     */
    void deleteResolution(UUID id);
}
