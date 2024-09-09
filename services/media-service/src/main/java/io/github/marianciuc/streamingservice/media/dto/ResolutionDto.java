/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionRequest.java
 *
 */

package io.github.marianciuc.streamingservice.media.dto;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Valid
public record ResolutionDto(
        UUID id,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name cannot exceed 255 characters")
        String name,

        @Size(max = 255, message = "Description cannot exceed 255 characters")
        String description,

        @NotNull(message = "Height cannot be null")
        @Min(value = 1, message = "Height must be greater than 0")
        Integer height,

        @NotNull(message = "Width cannot be null")
        @Min(value = 1, message = "Width must be greater than 0")
        Integer width,

        @NotNull(message = "Bitrate cannot be null")
        @Min(value = 1, message = "Bitrate must be greater than 0")
        Integer bitrate
) {
        public static ResolutionDto toResolutionDto(Resolution resolution) {
                return new ResolutionDto(
                        resolution.getId(),
                        resolution.getName(),
                        resolution.getDescription(),
                        resolution.getHeight(),
                        resolution.getWidth(),
                        resolution.getBitrate()
                );
        }

        public static Resolution toEntity(ResolutionDto resolutionDto) {
                return Resolution.builder().id(resolutionDto.id).build();
        }
}
