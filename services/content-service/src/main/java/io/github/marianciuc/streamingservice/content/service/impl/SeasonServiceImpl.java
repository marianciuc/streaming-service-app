/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: SeasonServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.SeasonDto;
import io.github.marianciuc.streamingservice.content.entity.Season;
import io.github.marianciuc.streamingservice.content.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {
    @Override
    public UUID createSeason(SeasonDto seasonDto) {
        return null;
    }

    @Override
    public void updateSeason(UUID id, SeasonDto seasonDto) {

    }

    @Override
    public SeasonDto findSeason(UUID id) {
        return null;
    }

    @Override
    public Season findSeasonEntity(UUID id) {
        return null;
    }

    @Override
    public void deleteSeason(UUID id) {

    }

    @Override
    public SeasonDto findSeasonByParams(UUID contentId, Integer number) {
        return null;
    }
}
