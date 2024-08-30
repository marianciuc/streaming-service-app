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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUBSCRIBED_USER', 'ROLE_CONTENT_MODERATOR')")
    @GetMapping(value = "/video/{video-id}", produces = "video/mp4")
    public ResponseEntity<Resource> streamVideo(@PathVariable("video-id") UUID videoId, HttpServletRequest request) {
        ResourceDto videoResponse = videoService.getVideoResource(videoId, request);

        return ResponseEntity.status(videoResponse.status())
                .contentType(MediaType.parseMediaType(videoResponse.contentType()))
                .header(HttpHeaders.CONTENT_LENGTH, videoResponse.rangeLength())
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + videoResponse.rangeStart() + "-" + videoResponse.rangeEnd() + "/" + videoResponse.fileLength())
                .body(new InputStreamResource(videoResponse.resource()));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/upload/video")
    public ResponseEntity<UUID> upload(
            @RequestParam(value = "media-type") io.github.marianciuc.streamingservice.media.entity.MediaType mediaType,
            @RequestParam(value = "content-id") UUID contentId,
            @RequestParam(value = "source-quality") UUID sourceResolutionId,
            @RequestParam(value = "file") @VideoFile MultipartFile file
    ) {
        videoService.uploadVideo(mediaType, contentId, sourceResolutionId, file);
        return ResponseEntity.ok().build();
    }
}
