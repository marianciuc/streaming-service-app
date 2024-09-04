/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.jwtsecurity.entity.JwtUser;
import io.github.marianciuc.jwtsecurity.service.UserService;
import io.github.marianciuc.streamingservice.media.clients.ContentClient;
import io.github.marianciuc.streamingservice.media.dto.ConvertingTaskDto;
import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.entity.VideoFileMetadata;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.enums.StatusType;
import io.github.marianciuc.streamingservice.media.enums.VideoStatues;
import io.github.marianciuc.streamingservice.media.exceptions.ImageNotFoundException;
import io.github.marianciuc.streamingservice.media.handlers.ChunkUploadHandler;
import io.github.marianciuc.streamingservice.media.kafka.TaskProducer;
import io.github.marianciuc.streamingservice.media.repository.VideoFileMetadataRepository;
import io.github.marianciuc.streamingservice.media.repository.VideoRepository;
import io.github.marianciuc.streamingservice.media.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.marianciuc.streamingservice.media.services.VideoStorageService.PATH_CHUNK_TEMPLATE;

/**
 * Service implementation for handling video-related operations.
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private static final long CHUNK_SIZE = 10 * 1024 * 1024;
    private static final String RESOLUTION_STARTED = "Compressing to resolution started.";
    private static final String RESOLUTION_FINISHED = "Compressing to resolution finished.";

    private final ChunkStateService chunkStateService;
    private final ChunkUploadHandler chunkUploadHandler;
    private final VideoRepository videoRepository;
    private final VideoFileMetadataRepository videoFileMetadataRepository;
    private final UserService userService;
    private final ContentClient contentClient;
    private final ResolutionService resolutionService;
    private final TaskProducer taskProducer;
    private final VideoUploadStatusService videoUploadStatusService;
    private final VideoCompressingService videoCompressingService;
    private final VideoStorageService videoStorage;

    @Override
    public void uploadVideo(MultipartFile chunk, Integer chunkNumber, UUID id, Integer totalChunks) {
        videoStorage.uploadVideoChunk(chunk, chunkNumber, id);
        chunkUploadHandler.handleChunkUpload(id, chunkNumber, totalChunks);
    }

    @Override
    public void deleteVideoByContent(UUID contentId) {
    }

    /**
     * Processes a media file by compressing it to the specified resolution and updating relevant database records.
     *
     * @param task the converting task containing the details of the media file to be processed,
     * including the unique identifier and target resolution information
     */
    @Override
    public void processMediaFile(ConvertingTaskDto task) {
        Video video = this.getVideoById(task.id());
        createVideoUploadStatus(task.id(), RESOLUTION_STARTED, task.resolutionDto().name());

        try (InputStream inputFileStream = videoStorage.assembleVideo(task.id())) {
            String playlist = videoCompressingService.compressVideoAndUploadToStorage(inputFileStream, task.resolutionDto(), task.id());
            createVideoUploadStatus(task.id(), RESOLUTION_FINISHED, task.resolutionDto().name());

            VideoFileMetadata videoFileMetadata = createAndSaveVideoFileMetadata(task, video, playlist);
            videoRepository.incrementResolutionCounter(videoFileMetadata.getId(), 1);

            if (isProcessingComplete(video.getResolutionsProcessed(), task)) {
                finalizeProcessing(video);
            }
        } catch (IOException e) {
            handleProcessingError(task.id(), e, task.resolutionDto().name());
        }
    }

    private void createVideoUploadStatus(UUID id, String message, String resolutionName) {
        videoUploadStatusService.createVideoUploadStatus(id, message, StatusType.INFO, "Compressing to " + resolutionName);
    }

    /**
     * Creates and saves VideoFileMetadata for a given video and playlist.
     *
     * @param task the converting task containing details of the media file to be processed,
     *             including unique identifier and target resolution information
     * @param video the video file for which metadata is being created
     * @param playlist the path to the playlist associated with the video file
     * @return the saved VideoFileMetadata object
     */
    private VideoFileMetadata createAndSaveVideoFileMetadata(ConvertingTaskDto task, Video video, String playlist) {
        VideoFileMetadata videoFileMetadata = VideoFileMetadata.builder()
                .contentType(video.getContentType())
                .video(video)
                .playListPath(playlist)
                .resolution(resolutionService.getEntityById(task.resolutionDto().id()))
                .build();
        return videoFileMetadataRepository.save(videoFileMetadata);
    }

    /**
     * Checks whether the processing of a video task is complete by comparing the number of resolutions
     * processed with the resolution counter in the database.
     *
     * @param resolutionsProcessed The number of resolutions that have been processed.
     * @param task The converting task containing the details of the media file to be processed,
     * including the unique identifier.
     * @return {@code true} if the resolution counter matches the number of processed resolutions,
     * {@code false} otherwise.
     * @throws ImageNotFoundException if the video associated with the task ID is not found.
     */
    private boolean isProcessingComplete(int resolutionsProcessed, ConvertingTaskDto task) {
        int resolutionCounter = videoRepository.findVideoById(task.id())
                .orElseThrow(() -> new ImageNotFoundException("Video not found"))
                .getResolutionCounter();
        return resolutionCounter == resolutionsProcessed;
    }

    private void handleProcessingError(UUID id, IOException e, String resolutionName) {
        videoUploadStatusService.createVideoUploadStatus(id, "Error compressing video to " + resolutionName, StatusType.ERROR, "Error compressing to " + resolutionName);
        throw new RuntimeException(e);
    }

    /**
     * Finalizes the processing of a given video by updating its status, creating a video upload status entry,
     * saving the video in the repository, creating a master playlist, and deleting the original video file from storage.
     *
     * @param video the video object that has completed processing
     */
    private void finalizeProcessing(Video video) {
        video.setStatus(VideoStatues.PROCESSED);
        videoUploadStatusService.createVideoUploadStatus(video.getId(), "Processing video finished", StatusType.INFO, "Processing video finished");
        videoRepository.save(video);
        createMasterPlaylist(video);
        videoStorage.deleteVideo(video.getId());
    }

    /**
     * Creates a master playlist for a given video by generating the playlist content,
     * creating the playlist link, and uploading the playlist.
     *
     * @param video the video object for which the master playlist is being created
     */
    @Override
    public void createMasterPlaylist(Video video) {
        String masterPlaylistContent = generateMasterPlaylistContent(video);
        String masterPlaylistLink = createMasterPlaylistLink(video.getId());
        uploadMasterPlaylist(video, masterPlaylistContent, masterPlaylistLink);
    }

    private String generateMasterPlaylistContent(Video video) {
        return video.getFiles().stream()
                .map(file -> String.format("#EXT-X-STREAM-INF:BANDWIDTH=%d,RESOLUTION=%dx%d\n%s\n",
                        file.getResolution().getBitrate(),
                        file.getResolution().getWidth(),
                        file.getResolution().getHeight(),
                        file.getPlayListPath()))
                .collect(Collectors.joining("", "#EXTM3U\n", ""));
    }

    private String createMasterPlaylistLink(UUID videoId) {
        return String.format("video/hls/%s/master.m3u8", videoId);
    }

    private void uploadMasterPlaylist(Video video, String content, String link) {
        try {
            videoStorage.uploadFile(link, new ByteArrayInputStream(content.getBytes()), content.length(), "application/vnd.apple.mpegurl");
            video.setMasterPlaylistPath(link);
            videoRepository.save(video);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save master playlist", e);
        }
    }

    /**
     * Retrieves the master playlist resource for a given video.
     *
     * @param videoId the unique identifier of the video whose master playlist is to be retrieved
     * @return the master playlist as a Resource
     */
    @Override
    public Resource getMasterPlaylistResource(UUID videoId) {
        String masterPlaylistPath = this.getVideoById(videoId).getMasterPlaylistPath();
        return new InputStreamResource(videoStorage.getFileInputStream(masterPlaylistPath));
    }

    /**
     * Retrieves a video segment resource from the storage.
     *
     * @param videoId the unique identifier of the video
     * @param resolution the resolution of the video segment
     * @param chunkIndex the index of the video segment chunk
     * @return the video segment as a Resource
     */
    @Override
    public Resource getVideoSegmentResource(UUID videoId, int resolution, int chunkIndex) {
        String segmentPath = String.format(PATH_CHUNK_TEMPLATE, resolution, videoId, chunkIndex);
        return new InputStreamResource(videoStorage.getFileInputStream(segmentPath));
    }

    /**
     * Processes a video by updating its resolution count and creating tasks for each resolution to be processed.
     *
     * @param id the unique identifier of the video to be processed
     */
    @Override
    public void processVideo(UUID id) {
        Video video = this.getVideoById(id);
        List<ResolutionDto> resolutionList = resolutionService.getAllResolutions();
        video.setResolutionCounter(resolutionList.size());
        createVideoUploadStatus(id, "Processing video", "video");

        resolutionList.forEach(resolutionDto -> taskProducer.sendTaskToQueue(id, resolutionDto));
    }

    /**
     * Prepares a video for upload by creating a new Video instance, calculating the total number of chunks,
     * and initializing chunk upload statuses.
     *
     * @param contentType the MIME type of the content, such as "video/mp4"
     * @param fileSize the total size of the video file in bytes
     * @param mediaType the type of media (e.g., MOVIE, EPISODE)
     * @param contentId the unique identifier for the content
     * @return an UploadMetadataDto containing the temporary file ID and the total number of chunks
     */
    @Override
    public UploadMetadataDto prepareVideo(String contentType, long fileSize, MediaType mediaType, UUID contentId) {
        JwtUser jwtUser = (JwtUser) userService.getUser();
        int totalChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);

        Video newVideo = createVideo(contentType, mediaType, contentId, jwtUser, totalChunks);
        Video video = saveVideoAndInitializeChunks(mediaType, contentId, newVideo);

        return new UploadMetadataDto(video.getId(), video.getTotalChunks());
    }

    private Video createVideo(String contentType, MediaType mediaType, UUID contentId, JwtUser jwtUser, int totalChunks) {
        return Video.builder()
                .status(VideoStatues.PREPARED)
                .contentType(contentType)
                .userId(jwtUser.getId())
                .mediaType(mediaType)
                .resolutionCounter(0)
                .resolutionsProcessed(0)
                .contentId(contentId)
                .totalChunks(totalChunks)
                .build();
    }

    private Video saveVideoAndInitializeChunks(MediaType mediaType, UUID contentId, Video newVideo) {
        Video video = videoRepository.save(newVideo);
        chunkStateService.createChunkUploadStatus(video.getId(), video.getTotalChunks());
        ResponseEntity<Void> response = sendMediaIdToContentService(mediaType, contentId, video);

        if (response.getStatusCode().isError()) {
            handleFailedMediaAddition(video);
        }

        return video;
    }

    private ResponseEntity<Void> sendMediaIdToContentService(MediaType mediaType, UUID contentId, Video video) {
        return (mediaType.equals(MediaType.EPISODE))
                ? contentClient.addEpisodeMediaId(video.getId(), contentId)
                : contentClient.addMovieMediaId(video.getId(), contentId);
    }

    private void handleFailedMediaAddition(Video video) {
        videoRepository.delete(video);
        chunkStateService.deleteChunkUploadStatus(video.getId());
        throw new RuntimeException("Failed to add media id to content");
    }

    /**
     * Retrieves a Video object by its unique identifier.
     *
     * @param videoId the unique identifier of the video to be retrieved
     * @return the Video object associated with the given identifier
     */
    @Override
    public Video getVideoById(UUID videoId) {
        return videoRepository.findVideoById(videoId).orElseThrow(() -> new ImageNotFoundException("Video not found"));
    }
}