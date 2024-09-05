/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EpisodeServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.dto.CreateMasterPlayListMessage;
import io.github.marianciuc.streamingservice.content.dto.EpisodeDto;
import io.github.marianciuc.streamingservice.content.entity.Episode;
import io.github.marianciuc.streamingservice.content.entity.Season;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import io.github.marianciuc.streamingservice.content.exceptions.InvalidArgumentsException;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.content.repository.EpisodeRepository;
import io.github.marianciuc.streamingservice.content.service.EpisodeService;
import io.github.marianciuc.streamingservice.content.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository repository;
    private final SeasonService seasonService;

    @Override
    public UUID createEpisode(EpisodeDto episodeDto) {
        Season season = seasonService.findSeasonEntity(episodeDto.seasonId());
        boolean episodeExists = season.getEpisodes().stream()
                .anyMatch(e -> e.getNumber().equals(episodeDto.number()));

        if (episodeExists) {
            throw new InvalidArgumentsException("Episode already exists");
        }

        Episode episode = Episode.builder()
                .title(episodeDto.title())
                .duration(episodeDto.duration())
                .number(episodeDto.number())
                .recordStatus(RecordStatus.HIDDEN)
                .season(season)
                .build();

        return repository.save(episode).getId();
    }

    @Override
    public UUID updateEpisode(UUID episodeId, EpisodeDto episodeDto) {
        return null;
    }

    @Override
    public EpisodeDto getEpisode(UUID episodeId) {
        return EpisodeDto.toEpisodeDto(this.getEpisodeEntity(episodeId));
    }

    @Override
    public Episode getEpisodeEntity(UUID episodeId) {
        return repository.findById(episodeId)
                .orElseThrow(() -> new NotFoundException("Episode not found"));
    }

    @Override
    public void deleteEpisode(UUID episodeId) {

    }

    @Override
    public void updateEpisodeMasterPlaylist(CreateMasterPlayListMessage message) {
        if (message.url().isEmpty()) return;
    }

    @Override
    public EpisodeDto findEpisodeByParams(UUID contentId, Integer seasonNumber, Integer episodeNumber) {
        return null;
    }
}
