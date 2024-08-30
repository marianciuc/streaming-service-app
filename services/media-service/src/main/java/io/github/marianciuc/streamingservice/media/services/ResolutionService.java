/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResolutionRequest;
import io.github.marianciuc.streamingservice.media.dto.ResolutionResponse;
import io.github.marianciuc.streamingservice.media.entity.Resolution;

import java.util.List;
import java.util.UUID;

public interface ResolutionService {
    ResolutionResponse createResolution(ResolutionRequest request);
    ResolutionResponse updateResolution(ResolutionRequest request);
    List<ResolutionResponse> getAllResolutions();
    ResolutionResponse getResolutionById(UUID id);
    Resolution getEntityById(UUID id);
    void deleteResolution(UUID id);
    List<Resolution> getAllEntities();
}
