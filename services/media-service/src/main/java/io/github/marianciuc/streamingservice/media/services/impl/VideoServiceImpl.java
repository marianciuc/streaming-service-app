/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.jwtsecurity.service.UserService;
import io.github.marianciuc.streamingservice.media.dto.ResourceDto;
import io.github.marianciuc.streamingservice.media.dto.VideoMetadataDto;
import io.github.marianciuc.streamingservice.media.entity.Media;
import io.github.marianciuc.streamingservice.media.entity.MediaType;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.kafka.KafkaVideoProducer;
import io.github.marianciuc.streamingservice.media.mappers.MediaMapper;
import io.github.marianciuc.streamingservice.media.repository.MediaRepository;
import io.github.marianciuc.streamingservice.media.services.ResolutionService;
import io.github.marianciuc.streamingservice.media.services.VideoCompressingService;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final MediaRepository mediaRepository;
    private final ResolutionService resolutionService;
    private final VideoCompressingService compressingService;
    private final UserService userService;
    private final KafkaVideoProducer kafkaVideoProducer;
    private final MediaMapper mediaMapper;

    @Override
    public void uploadVideo(MediaType mediaType, UUID contentId, UUID resolutionId, MultipartFile file) {
        Resolution originalResolution = this.resolutionService.getEntityById(resolutionId);

        List<Resolution> resolutions = this.resolutionService
                .getAllEntities().stream().filter(resolution -> (resolution.getBitrate() <= originalResolution.getBitrate())).toList();

        for (Resolution resolution : resolutions) {
            byte[] compressedVideo = compressingService.compressVideo(file, resolution);
            Media media = Media.builder()
                    .data(compressedVideo)
                    .authorId(userService.getUser().getId())
                    .contentType(file.getContentType())
                    .mediaType(mediaType)
                    .contentLength(compressedVideo.length)
                    .contentId(contentId)
                    .createdAt(LocalDateTime.now())
                    .resolution(resolution)
                    .build();
            VideoMetadataDto savedMedia = mediaMapper.toVideoMetadataDto(mediaRepository.save(media));
            kafkaVideoProducer.sendUploadedVideoTopic(savedMedia);
        }
    }

    @Override
    public void deleteVideo(UUID id) {

    }

    @Override
    public ResourceDto getVideoResource(UUID videoId, HttpServletRequest request) {
        return null;
    }

    @Override
    public void deleteVideoByContent(UUID contentId) {

    }
}
