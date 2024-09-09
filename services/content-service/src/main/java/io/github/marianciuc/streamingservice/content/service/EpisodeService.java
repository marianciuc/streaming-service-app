/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EpisodeService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.kafka.messages.CreateMasterPlayListMessage;
import io.github.marianciuc.streamingservice.content.dto.EpisodeDto;
import io.github.marianciuc.streamingservice.content.entity.Episode;
import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;

import java.util.UUID;

/**
 * Service interface for managing episodes.
 */
public interface EpisodeService {

    /**
     * Creates a new episode by the given episode DTO. The episode is associated with a season. The season must exist.
     * After creation, the episode status is {@code HIDDEN}.
     * @param episodeDto the episode DTO
     * @return the ID of the created episode
     * @throws NotFoundException if the season associated with the episode is not found
     */
    UUID createEpisode(EpisodeDto episodeDto);

    /**
     * Updates the episode with the given ID by the given episode DTO. Updates only the fields that are not null.
     * @param episodeId the ID of the episode to update
     * @param episodeDto the episode DTO
     * @return the ID of the updated episode
     * @throws NotFoundException if the episode with the given ID is not found
     */
    UUID updateEpisode(UUID episodeId, EpisodeDto episodeDto);

    /**
     * Retrieves an episode by its ID.
     * @param episodeId the ID of the episode to retrieve
     * @return the EpisodeDto object representing the episode with the given ID
     * @throws NotFoundException if the episode with the given ID is not found
     */
    EpisodeDto getEpisode(UUID episodeId);

    /**
     * Retrieves an episode entity by its ID.
     * @param episodeId the ID of the episode entity to retrieve
     * @return the Episode object representing the episode entity with the given ID
     * @throws NotFoundException if the episode entity with the given ID is not found
     */
    Episode getEpisodeEntity(UUID episodeId);

    /**
     * Deletes the episode from db with the given ID.
     * @param episodeId the ID of the episode to delete
     * @throws NotFoundException if the episode with the given ID is not found
     */
    void deleteEpisode(UUID episodeId);

    /**
     * Updates the episode master playlist link and changes status to {@code ACTIVE} if media file was uploaded successfully.
     * @param message the message containing the master playlist link and master playlist id to update
     * @throws NotFoundException if the episode with the given ID is not found
     */
    void updateEpisodeMasterPlaylist(CreateMasterPlayListMessage message);

    /**
     * Retrieves an episode by the content ID, season number, and episode number.
     * @param contentId the ID of the content associated with the episode
     * @param seasonNumber the number of the season associated with the episode
     * @param episodeNumber the number of the episode
     * @return the EpisodeDto object representing the episode
     * @throws NotFoundException if the episode with the given content ID, season number, and episode number is not found
     */
    EpisodeDto findEpisodeByParams(UUID contentId, Integer seasonNumber, Integer episodeNumber);
}
