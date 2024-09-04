/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoController.java
 *
 */

package io.github.marianciuc.streamingservice.media.controllers;

import io.github.marianciuc.streamingservice.media.dto.UploadMetadataDto;
import io.github.marianciuc.streamingservice.media.handlers.ChunkUploadHandler;
import io.github.marianciuc.streamingservice.media.services.VideoStorageService;
import io.github.marianciuc.streamingservice.media.validation.VideoFile;
import io.minio.GetObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/media/video")
@RequiredArgsConstructor
@Slf4j
public class VideoController {

    private static final String PARAM_CONTENT_ID = "contentId";
    private static final String PARAM_SOURCE_QUALITY = "sourceQuality";
    private static final String PARAM_FILE = "file";
    private static final String PARAM_TOTAL_CHUNKS = "totalChunks";
    private static final String PARAM_CHUNK_NUMBER = "chunkNumber";
    private static final String PARAM_TEMP_FILE_ID = "tempFileName";

    private static final long CHUNK_SIZE = 10 * 1024 * 1024;

    private final ChunkUploadHandler chunkUploadHandler;
    private final VideoStorageService videoStorageService;


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
            videoStorageService.uploadVideoChunk(chunk, chunkNumber, tempFileId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while chunk uploading.");
        }
        chunkUploadHandler.handleChunkUpload(tempFileId, chunkNumber, totalChunks, contentId, sourceResolutionId);
        return ResponseEntity.ok("Chunk successfully uploaded.");
    }


    @GetMapping("/master/{fileId}")
    public ResponseEntity<InputStreamResource> getMasterPlaylist(@PathVariable UUID fileId) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileId + "/master.m3u8")
                            .build()
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=master.m3u8")
                    .contentType(MediaType.parseMediaType("application/vnd.apple.mpegurl"))
                    .body(new InputStreamResource(inputStream));
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            log.error("Error occurred while fetching master playlist for file {}: {}", fileId, e.toString());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/segment/{fileId}/{resolution}/{segment}")
    public ResponseEntity<InputStreamResource> getSegment(@PathVariable UUID fileId,
                                                          @PathVariable String resolution,
                                                          @PathVariable String segment) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileId + "/" + resolution + "/" + segment)
                            .build()
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + segment)
                    .contentType(MediaType.parseMediaType("video/MP2T"))
                    .body(new InputStreamResource(inputStream));
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            log.error("Error occurred while fetching segment {} for file {}: {}", segment, fileId, e.toString());
            return ResponseEntity.status(500).build();
        }
    }

}
