/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionMapper.java
 *
 */

package io.github.marianciuc.streamingservice.media.mappers;

import io.github.marianciuc.streamingservice.media.dto.ResolutionResponse;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import org.springframework.stereotype.Component;

@Component
public class ResolutionMapper {
    public ResolutionResponse toResponse(Resolution resolution) {
        return new ResolutionResponse(
                resolution.getId(),
                resolution.getDescription(),
                resolution.getName()
        );
    }
}
