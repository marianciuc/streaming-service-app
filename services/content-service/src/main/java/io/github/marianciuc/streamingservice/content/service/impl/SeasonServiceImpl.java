/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: SeasonServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.ContentDto;
import io.github.marianciuc.streamingservice.content.dto.SeasonDto;
import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.entity.Season;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.repository.SeasonRepository;
import io.github.marianciuc.streamingservice.content.service.ContentService;
import io.github.marianciuc.streamingservice.content.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository repository;
    private ContentService contentService;

    @Override
    public UUID createSeason(SeasonDto seasonDto) {
        Content content = contentService.getContentEntity(seasonDto.contentId());

        Season season = Season.builder()
                .content(content)
                .number(seasonDto.number())
                .title(seasonDto.title())
                .description(seasonDto.description())
                .build();
        return repository.save(season).getId();
    }

    @Override
    public void updateSeason(UUID id, SeasonDto seasonDto) {
        Season season = this.findSeasonEntity(id);
        if (!seasonDto.title().isEmpty()) season.setTitle(seasonDto.title());
        if (!seasonDto.description().isEmpty()) season.setDescription(seasonDto.description());
        repository.save(season);
    }

    @Override
    public SeasonDto findSeason(UUID id) {
        return SeasonDto.toSeasonDto(this.findSeasonEntity(id));
    }

    @Override
    public Season findSeasonEntity(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Season not found"));
    }

    @Override
    @Transactional
    public void deleteSeason(UUID id) {
        Season season = this.findSeasonEntity(id);
        if (season.getEpisodes().isEmpty()) {
            repository.delete(season);
        } else {
            season.getEpisodes().stream().forEach(e -> e.setRecordStatus(RecordStatus.DELETED));
            season.setRecordStatus(RecordStatus.DELETED);
            repository.save(season);
        }
    }

    @Override
    public SeasonDto findSeasonByParams(UUID contentId, Integer number) {
        ContentDto content = contentService.getContent(contentId);
        return content.seasonList().stream()
                .filter(s -> s.number().equals(number))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Season not found"));
    }
}
