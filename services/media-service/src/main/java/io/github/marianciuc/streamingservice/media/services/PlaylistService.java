/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PlaylistService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import org.springframework.core.io.Resource;

import java.util.UUID;

/**
 * This interface provides methods for creating and retrieving video playlists.
 */
public interface PlaylistService {

    /**
     * This method creates a master playlist for the video and stores it via VideoStorageService and save link to
     * Video entity
     * @param videoId the unique identifier of the video
     * @throws RuntimeException if an error occurs during the master playlist creation process
     */
    void createMasterPlaylist(UUID videoId);

    /**
     * Returns the master playlist resource for the specified video.
     * @param videoId the unique identifier of the video
     * @return the master playlist resource
     */
    Resource getMasterPlaylistResource(UUID videoId);

    /**
     * @param videoId the unique identifier of the video
     * @param resolution the resolution height of the video
     * @param chunkIndex the index of the video chunk
     * @return the video segment resource
     */
    Resource getVideoSegmentResource(UUID videoId, int resolution, int chunkIndex);
}
