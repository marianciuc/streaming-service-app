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

import java.util.UUID;

public interface SeasonService {
    UUID createSeason(SeasonDto seasonDto);
    void updateSeason(UUID id, SeasonDto seasonDto);
    SeasonDto findSeason(UUID id);
    Season findSeasonEntity(UUID id);
    void deleteSeason(UUID id);
    SeasonDto findSeasonByParams(UUID contentId, Integer number);
}
