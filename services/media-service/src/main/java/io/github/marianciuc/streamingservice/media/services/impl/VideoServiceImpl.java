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
import io.github.marianciuc.streamingservice.media.exceptions.MediaContentNotFoundException;
import io.github.marianciuc.streamingservice.media.kafka.KafkaVideoProducer;
import io.github.marianciuc.streamingservice.media.mappers.MediaMapper;
import io.github.marianciuc.streamingservice.media.repository.MediaRepository;
import io.github.marianciuc.streamingservice.media.services.ResolutionService;
import io.github.marianciuc.streamingservice.media.services.VideoCompressingService;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final static String NO_CONTENT_MSG = "Video not found";
    private final static String RANGE_HEADER = "Range";

    private final MediaRepository mediaRepository;
    private final ResolutionService resolutionService;
    private final VideoCompressingService compressingService;
    private final UserService userService;
    private final MediaMapper mediaMapper;
    private final KafkaVideoProducer kafkaVideoProducer;

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
        Media media = mediaRepository.findById(id).orElseThrow(() -> new MediaContentNotFoundException(NO_CONTENT_MSG));
        kafkaVideoProducer.sendDeletedVideoTopic(mediaMapper.toVideoMetadataDto(media));
        mediaRepository.delete(media);
    }

    @Override
    public ResourceDto getVideoResource(UUID videoId, HttpServletRequest request) {
        Media media = mediaRepository.findById(videoId).orElseThrow(() -> new MediaContentNotFoundException(NO_CONTENT_MSG));
        byte[] mediaData = media.getData();

        String rangeHeader = request.getHeader(RANGE_HEADER);
        long fileLength = media.getContentLength();
        long rangeStart = 0;
        long rangeEnd = fileLength - 1;

        if (rangeHeader != null) {
            long[] rangeValues = parseRangeHeader(rangeHeader);
            rangeStart = rangeValues[0];
            rangeEnd = rangeValues[1] > 0 ? rangeValues[1] : rangeEnd;
        }

        long rangeLength = rangeEnd - rangeStart + 1;
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(mediaData, (int) rangeStart, (int) rangeLength));

        return new ResourceDto(
                HttpStatus.PARTIAL_CONTENT,
                media.getContentType(),
                String.valueOf(rangeLength),
                rangeStart,
                rangeEnd,
                fileLength,
                resource
        );
    }

    private long[] parseRangeHeader(String rangeHeader) {
        long rangeStart = 0;
        long rangeEnd = -1;
        String[] ranges = rangeHeader.replace("bytes=", "").split("-");
        try {
            rangeStart = Long.parseLong(ranges[0]);
            if (ranges.length > 1) {
                rangeEnd = ranges[1].isEmpty() ? -1 : Long.parseLong(ranges[1]);
            }
        } catch (NumberFormatException ignored) {
        }
        return new long[]{rangeStart, rangeEnd};
    }

    @Override
    public void deleteVideoByContent(UUID contentId) {
        List<Media> mediaList = mediaRepository.findAllByContentId(contentId);
        mediaRepository.deleteAll(mediaList);
    }
}
