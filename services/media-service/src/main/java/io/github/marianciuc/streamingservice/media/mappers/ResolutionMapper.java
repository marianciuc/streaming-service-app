/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionMapper.java
 *
 */

package io.github.marianciuc.streamingservice.media.mappers;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.dto.ResolutionMessage;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import org.springframework.stereotype.Component;

@Component
public class ResolutionMapper {
    public ResolutionMessage toResponse(Resolution resolution) {
        return new ResolutionMessage(
                resolution.getId(),
                resolution.getDescription(),
                resolution.getName()
        );
    }

    public ResolutionDto toResolutionDto(Resolution resolution) {
        return new ResolutionDto(
                resolution.getId(),
                resolution.getName(),
                resolution.getDescription(),
                resolution.getHeight(),
                resolution.getWidth(),
                resolution.getBitrate()
        );
    }
}
