/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoController.java
 *
 */

package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.dto.VideoFileUploadStatusDto;
import io.github.marianciuc.streamingservice.media.services.UploadVideoService;
import io.github.marianciuc.streamingservice.media.services.VideoFileMetadataService;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import io.github.marianciuc.streamingservice.media.services.VideoUploadStatusService;
import io.github.marianciuc.streamingservice.media.validation.VideoFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import io.github.marianciuc.streamingservice.media.enums.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * The VideoController class provides REST API endpoints for handling
 * video uploads, including generating metadata and uploading video chunks.
 */
@RestController
@RequestMapping("/api/v1/media/video")
@RequiredArgsConstructor
@Slf4j
public class VideoController {

    private static final String PARAM_CONTENT_ID = "contentId";
    private static final String PARAM_CONTENT_TYPE = "contentType";
    private static final String PARAM_MEDIA_TYPE = "mediaType";
    private static final String PARAM_FILE = "file";
    private static final String PARAM_FILE_SIZE = "fileSize";
    private static final String PARAM_TOTAL_CHUNKS = "totalChunks";
    private static final String PARAM_CHUNK_NUMBER = "chunkNumber";
    private static final String PARAM_FILE_ID = "tempFileName";

    private final UploadVideoService uploadVideoService;
    private final VideoUploadStatusService videoUploadStatusService;


    /**
     * Generates metadata for the video upload process.
     *
     * @param fileSize - The size of the file being uploaded.
     * @param contentId - The unique identifier for the content.
     * @param contentType - The type of content being uploaded.
     * @param mediaType - The media type (e.g., MOVIE, EPISODE) of the content.
     * @return A ResponseEntity containing the generated upload metadata.
     */
    @GetMapping("/upload/prepare")
    public ResponseEntity<UploadMetadataDto> generateMetadata(
            @RequestParam(value = PARAM_FILE_SIZE) long fileSize,
            @RequestParam(value = PARAM_CONTENT_ID) UUID contentId,
            @RequestParam(value = PARAM_CONTENT_TYPE) String contentType,
            @RequestParam(value = PARAM_MEDIA_TYPE) MediaType mediaType
    ) {
        return ResponseEntity.ok(uploadVideoService.prepareVideo(contentType, fileSize, mediaType, contentId));
    }

    /**
     * Uploads a chunk of a video file.
     *
     * @param fileId The unique identifier of the file being uploaded.
     * @param chunkNumber The sequential number of the chunk being uploaded.
     * @param totalChunks The total number of chunks for the file.
     * @param chunk The video file chunk being uploaded.
     * @return A ResponseEntity containing a success message if the chunk is uploaded successfully
     * or an error message if there is an issue during the upload.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam(value = PARAM_FILE_ID) UUID fileId,
            @RequestParam(value = PARAM_CHUNK_NUMBER) Integer chunkNumber,
            @RequestParam(value = PARAM_TOTAL_CHUNKS) Integer totalChunks,
            @RequestParam(value = PARAM_FILE) @VideoFile MultipartFile chunk
    ) {
        uploadVideoService.uploadVideo(chunk, chunkNumber, fileId, totalChunks);
        return ResponseEntity.ok("Chunk successfully uploaded.");
    }

    @GetMapping("/statuses/{file-id}")
    public ResponseEntity<List<VideoFileUploadStatusDto>> getStatues(@PathVariable("file-id") UUID fileId) {
        return ResponseEntity.ok(videoUploadStatusService.getVideoUploadStatus(fileId));
    }
}
