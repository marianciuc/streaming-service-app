/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EpisodeService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.dto.CreateMasterPlayListMessage;
import io.github.marianciuc.streamingservice.content.dto.EpisodeDto;
import io.github.marianciuc.streamingservice.content.entity.Episode;

import java.util.UUID;

public interface EpisodeService {
    UUID createEpisode(EpisodeDto episodeDto);
    UUID updateEpisode(UUID episodeId, EpisodeDto episodeDto);
    EpisodeDto getEpisode(UUID episodeId);
    Episode getEpisodeEntity(UUID episodeId);
    void deleteEpisode(UUID episodeId);
    void updateEpisodeMasterPlaylist(CreateMasterPlayListMessage message);
    EpisodeDto findEpisodeByParams(UUID contentId, Integer seasonNumber, Integer episodeNumber);
}
