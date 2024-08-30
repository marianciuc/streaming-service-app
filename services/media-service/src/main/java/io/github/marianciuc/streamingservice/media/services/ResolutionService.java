/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.entity.Resolution;

import java.util.List;
import java.util.UUID;

public interface ResolutionService {
    ResolutionDto createResolution(ResolutionDto request);
    ResolutionDto updateResolution(ResolutionDto request);
    List<ResolutionDto> getAllResolutions();
    ResolutionDto getResolutionById(UUID id);
    Resolution getEntityById(UUID id);
    void deleteResolution(UUID id);
    List<Resolution> getAllEntities();
}
