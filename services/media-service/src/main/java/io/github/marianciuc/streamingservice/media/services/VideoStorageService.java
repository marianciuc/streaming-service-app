/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoStorageService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface VideoStorageService {

    String CONTENT_PATH = "videos/";
    String CHUNKS_PATH = "chunks/";

    void uploadFile(String objectName, InputStream inputStream, long size, String contentType) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException;
    void uploadVideoChunk(MultipartFile chunk, Integer chunkNumber, UUID fileId);
    void deleteVideo(UUID fileId);
    InputStream assembleVideo(UUID fileId);
}
