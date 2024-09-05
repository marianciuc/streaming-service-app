/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: SeasonDto.java
 *
 */

package io.github.marianciuc.streamingservice.content.dto;

import io.github.marianciuc.streamingservice.content.entity.Season;

import java.util.List;
import java.util.UUID;

public record SeasonDto(
        UUID id,
        String title,
        String description,
        List<EpisodeDto> episodes,
        UUID contentId,
        Integer number
) {
    public static SeasonDto toSeasonDto(Season season){
        return new SeasonDto(
                season.getId(),
                season.getTitle(),
                season.getDescription(),
                season.getEpisodes().stream().map(EpisodeDto::toEpisodeDto).toList(),
                season.getContent().getId(),
                season.getNumber()
        );
    }
}
