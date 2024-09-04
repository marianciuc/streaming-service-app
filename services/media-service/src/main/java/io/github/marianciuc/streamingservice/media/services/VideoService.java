/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ConvertingTaskDto;
import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VideoService {
    void uploadVideo(MultipartFile chunk, Integer chunkNumber, UUID id);

    /**
     * Deletes the video associated with the specified content identifier.
     *
     * @param contentId The unique identifier of the video content to be deleted.
     */
    void deleteVideoByContent(UUID contentId);

    /**
     * Processes the video identified by the provided unique identifier.
     *
     * @param videoId The unique identifier of the video to be processed.
     */
    void processVideo(UUID videoId);

    /**
     * Prepares the video for uploading by generating metadata required for the upload process.
     *
     * @param contentType The MIME type of the video content.
     * @param fileSize The size of the video file in bytes.
     * @param mediaType The type of media (e.g., MOVIE, EPISODE) being uploaded.
     * @param contentId The unique identifier for the content.
     * @return UploadMetadataDto containing metadata necessary for video uploading.
     */
    UploadMetadataDto prepareVideo(String contentType, long fileSize, MediaType mediaType, UUID contentId);

    /**
     * Processes the provided media file based on the details in the converting task.
     *
     * @param task The task containing details such as the media file identifier and resolution information
     */
    void processMediaFile(ConvertingTaskDto task);


    /**
     * Retrieves a video object based on its unique identifier.
     *
     * @param videoId The unique identifier of the video.
     * @return The video object that matches the specified identifier.
     */
    Video getVideoById(UUID videoId);

    /**
     * Creates a master playlist for a given video by generating the playlist content,
     * creating the playlist link, and uploading the playlist.
     *
     * @param video the video object for which the master playlist is being created
     */
    void createMasterPlaylist(Video video);


    /**
     * Retrieves the master playlist resource for a given video.
     *
     * @param videoId The unique identifier of the video.
     * @return The master playlist resource associated with the specified video.
     */
    Resource getMasterPlaylistResource(UUID videoId);

    /**
     * Retrieves a specific video segment resource based on the given parameters.
     *
     * @param videoId The unique identifier of the video.
     * @param resolution The resolution of the video segment.
     * @param chunkIndex The index of the segment chunk to retrieve.
     * @return The requested video segment resource.
     */
    Resource getVideoSegmentResource(UUID videoId, int resolution, int chunkIndex);
}
