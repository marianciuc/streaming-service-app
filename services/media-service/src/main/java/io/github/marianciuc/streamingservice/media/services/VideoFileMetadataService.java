/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoFileMetadataService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.dto.VideoDto;
import io.github.marianciuc.streamingservice.media.dto.VideoFileMetadataDto;

import java.util.UUID;

public interface VideoFileMetadataService {
    VideoFileMetadataDto createMetadata(VideoDto videoFileId, ResolutionDto resolutionDto);


    /**
     * Updates the metadata object with the given id with the given status and playlist path.
     * If you don`t want to update the status, pass null as the status parameter.
     * if you don`t want to update the playlist path, pass empty string as the playlistPath parameter.
     *
     * @param id the unique identifier of the metadata object
     * @param status the status of the video file processing
     * @param playlistPath the path to the playlist file
     */
    void updateMetadata(UUID id, Boolean status, String playlistPath);
    void deleteMetadata();
}
