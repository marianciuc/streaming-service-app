/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoController.java
 *
 */

package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.dto.ResourceDto;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import io.github.marianciuc.streamingservice.media.validation.VideoFile;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/media/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    private static final String HEADER_CONTENT_LENGTH = HttpHeaders.CONTENT_LENGTH;
    private static final String HEADER_CONTENT_RANGE = HttpHeaders.CONTENT_RANGE;

    private static final String PARAM_MEDIA_TYPE = "media-type";
    private static final String PARAM_CONTENT_ID = "content-id";
    private static final String PARAM_SOURCE_QUALITY = "source-quality";
    private static final String PARAM_FILE = "file";

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUBSCRIBED_USER', 'ROLE_CONTENT_MODERATOR')")
    @GetMapping(value = "/stream/{video-id}", produces = "video/mp4")
    public ResponseEntity<Resource> streamVideo(@PathVariable("video-id") UUID videoId, HttpServletRequest request) {
        ResourceDto videoResponse = videoService.getVideoResource(videoId, request);

        return ResponseEntity.status(videoResponse.status())
                .contentType(MediaType.parseMediaType(videoResponse.contentType()))
                .header(HEADER_CONTENT_LENGTH, videoResponse.rangeLength())
                .header(HEADER_CONTENT_RANGE, "bytes " + videoResponse.rangeStart() + "-" + videoResponse.rangeEnd() + "/" + videoResponse.fileLength())
                .body(new InputStreamResource(videoResponse.resource()));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/upload/video")
    public ResponseEntity<UUID> upload(
            @RequestParam(value = PARAM_MEDIA_TYPE) io.github.marianciuc.streamingservice.media.entity.MediaType mediaType,
            @RequestParam(value = PARAM_CONTENT_ID) UUID contentId,
            @RequestParam(value = PARAM_SOURCE_QUALITY) UUID sourceResolutionId,
            @RequestParam(value = PARAM_FILE) @VideoFile MultipartFile file
    ) {
        videoService.uploadVideo(mediaType, contentId, sourceResolutionId, file);
        return ResponseEntity.ok().build();
    }
}
