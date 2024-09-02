/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoController.java
 *
 */

package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.dto.ResourceDto;
import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.handlers.ChunkUploadHandler;
import io.github.marianciuc.streamingservice.media.services.UploadVideoService;
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

    private static final String HEADER_CONTENT_LENGTH = HttpHeaders.CONTENT_LENGTH;
    private static final String HEADER_CONTENT_RANGE = HttpHeaders.CONTENT_RANGE;

    private static final String PARAM_CONTENT_ID = "contentId";
    private static final String PARAM_SOURCE_QUALITY = "sourceQuality";
    private static final String PARAM_FILE = "file";
    private static final String PARAM_TOTAL_CHUNKS = "totalChunks";
    private static final String PARAM_CHUNK_NUMBER = "chunkNumber";
    private static final String PARAM_TEMP_FILE_ID = "tempFileName";

    private static final long CHUNK_SIZE = 10 * 1024 * 1024;

    private final VideoService videoService;
    private final UploadVideoService uploadVideoService;
    private final ChunkUploadHandler chunkUploadHandler;


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
    @GetMapping("/upload/generate-metadata")
    public ResponseEntity<UploadMetadataDto> generateMetadata(
            @RequestParam(value = "file-size") long fileSize
    ) {
        return ResponseEntity.ok(new UploadMetadataDto(UUID.randomUUID(), (int) (fileSize / CHUNK_SIZE)));
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam(value = PARAM_TEMP_FILE_ID) UUID tempFileId,
            @RequestParam(value = PARAM_CONTENT_ID) UUID contentId,
            @RequestParam(value = PARAM_SOURCE_QUALITY) UUID sourceResolutionId,
            @RequestParam(value = PARAM_CHUNK_NUMBER) Integer chunkNumber,
            @RequestParam(value = PARAM_TOTAL_CHUNKS) Integer totalChunks,
            @RequestParam(value = PARAM_FILE) @VideoFile MultipartFile chunk
    ) {
        String key = "chunks/" + tempFileId + "/chunk_" + chunkNumber;
        try {
            uploadVideoService.uploadVideoFile(key, chunk);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while chunk uploading.");
        }
        chunkUploadHandler.handleChunkUpload(tempFileId, chunkNumber, totalChunks, contentId, sourceResolutionId);
        return ResponseEntity.ok("Chunk successfully uploaded.");
    }

}
