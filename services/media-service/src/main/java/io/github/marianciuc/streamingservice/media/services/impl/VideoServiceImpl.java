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
import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.Video;
import io.github.marianciuc.streamingservice.media.entity.VideoFileMetadata;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import io.github.marianciuc.streamingservice.media.enums.StatusType;
import io.github.marianciuc.streamingservice.media.enums.VideoStatues;
import io.github.marianciuc.streamingservice.media.exceptions.MediaContentNotFoundException;
import io.github.marianciuc.streamingservice.media.kafka.TaskProducer;
import io.github.marianciuc.streamingservice.media.repository.VideoFileMetadataRepository;
import io.github.marianciuc.streamingservice.media.repository.VideoRepository;
import io.github.marianciuc.streamingservice.media.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private static final long CHUNK_SIZE = 10 * 1024 * 1024;

    private final ChunkStateService chunkStateService;
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
    public void uploadVideo(MediaType mediaType, UUID contentId, UUID resolutionId, MultipartFile file) {

    }

    @Override
    public void deleteVideo(UUID id) {

    }

    @Override
    public void deleteVideoByContent(UUID contentId) {

    }

    public void processMediaFile(UUID id, ResolutionDto resolutionDto) {
        Video video = videoRepository.findVideoById(id).orElseThrow(() -> new MediaContentNotFoundException("Video not found"));
        videoUploadStatusService.createVideoUploadStatus(id, "Compressing to resolution started.", StatusType.INFO, "Compressing to " + resolutionDto.name());
        try (InputStream inputFileStream = videoStorage.assembleVideo(id)) {

            String playlist = videoCompressingService.compressVideoAndUploadToStorage(inputFileStream, resolutionDto, id);
            videoUploadStatusService.createVideoUploadStatus(id, "Compressing to resolution finished.", StatusType.INFO, "Compressing to " + resolutionDto.name());

            VideoFileMetadata videoFileMetadata = VideoFileMetadata.builder()
                    .contentType(video.getContentType())
                    .video(video)
                    .playListPath(playlist)
                    .resolution(resolutionService.getEntityById(resolutionDto.id()))
                    .build();

            videoFileMetadata = videoFileMetadataRepository.save(videoFileMetadata);
            videoRepository.incrementResolutionCounter(videoFileMetadata.getId(), 1);
        } catch (IOException e) {
            videoUploadStatusService.createVideoUploadStatus(id, "Error while compressing video to " + resolutionDto.name() + "resolution", StatusType.ERROR, "Error compressing to " + resolutionDto.name());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processVideo(UUID id) {
        Video video = videoRepository.findVideoById(id).orElseThrow(() -> new MediaContentNotFoundException("Video not found"));
        List<ResolutionDto> resolutionList = resolutionService.getAllResolutions();

        video.setResolutionCounter(resolutionList.size());

        videoUploadStatusService.createVideoUploadStatus(id, "Processing video", StatusType.INFO, "Processing video");
        for (ResolutionDto resolutionDto : resolutionList) {
            taskProducer.sendTaskToQueue(id, resolutionDto);
        }
    }

    @Override
    public UploadMetadataDto prepareVideo(String contentType, long fileSize, MediaType mediaType, UUID contentId) {
        JwtUser jwtUser = (JwtUser) userService.getUser();

        int totalChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);

        Video newVideo = Video.builder()
                .status(VideoStatues.PREPARED)
                .contentType(contentType)
                .userId(jwtUser.getId())
                .mediaType(mediaType)
                .resolutionCounter(0)
                .resolutionsProcessed(0)
                .contentId(contentId)
                .totalChunks(totalChunks)
                .build();
        Video video = videoRepository.save(newVideo);
        chunkStateService.createChunkUploadStatus(video.getId(), totalChunks);

        ResponseEntity<Void> response;

        if (mediaType.equals(MediaType.EPISODE)) response = contentClient.addEpisodeMediaId(video.getId(), contentId);
        else response = contentClient.addMovieMediaId(video.getId(), contentId);

        if (response.getStatusCode().isError()) {
            videoRepository.delete(video);
            chunkStateService.deleteChunkUploadStatus(video.getId());
            throw new RuntimeException("Failed to add media id to content");
        }

        chunkStateService.createChunkUploadStatus(video.getId(), video.getTotalChunks());
        return new UploadMetadataDto(video.getId(), video.getTotalChunks());
    }

    @Override
    public Video getVideoById(UUID videoId) {
        return videoRepository.findVideoById(videoId).orElseThrow(() -> new MediaContentNotFoundException("Video not found"));
    }
}
