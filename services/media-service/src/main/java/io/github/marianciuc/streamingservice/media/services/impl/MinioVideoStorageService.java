/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MinioVideoStorageService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.services.VideoStorageService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioVideoStorageService implements VideoStorageService {

    @Value("${minio.bucket.name}")
    private String bucketName;

    private final MinioClient minioClient;

    private static final String NULL_ARGUMENT_EXCEPTION_MESSAGE = "Chunk, chunkNumber, and fileId must not be null";
    private static final String SUCCESS_UPLOAD_MESSAGE = "Successfully uploaded chunk {} for file {}";
    private static final String ERROR_UPLOAD_MESSAGE = "Error occurred while uploading chunk {} for file {}: {}";
    private static final String RUNTIME_UPLOAD_EXCEPTION_MESSAGE = "Could not upload chunk %d for file %s: %s";
    private static final String NULL_FILE_ID_EXCEPTION_MESSAGE = "fileId must not be null";
    private static final String SUCCESS_DELETE_CHUNK_MESSAGE = "Successfully deleted chunk {}";
    private static final String SUCCESS_DELETE_FILE_MESSAGE = "Successfully deleted all chunks for file {}";
    private static final String ERROR_DELETE_MESSAGE = "Error occurred while deleting file {}: {}";
    private static final String RUNTIME_DELETE_EXCEPTION_MESSAGE = "Could not delete file %s: %s";

    @Override
    public void uploadVideoChunk(MultipartFile chunk, Integer chunkNumber, UUID fileId) {
        if (chunk == null || chunkNumber == null || fileId == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        try (InputStream inputStream = chunk.getInputStream()) {
            String objectName = fileId + "/chunk" + chunkNumber;
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, chunk.getSize(), -1)
                            .contentType(chunk.getContentType())
                            .build()
            );
            log.info(SUCCESS_UPLOAD_MESSAGE, chunkNumber, fileId);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(ERROR_UPLOAD_MESSAGE, chunkNumber, fileId, e.toString());
            throw new RuntimeException(String.format(RUNTIME_UPLOAD_EXCEPTION_MESSAGE, chunkNumber, fileId, e.toString()), e);
        }
    }

    @Override
    public void deleteVideo(UUID fileId) {
        if (fileId == null) {
            throw new IllegalArgumentException(NULL_FILE_ID_EXCEPTION_MESSAGE);
        }
        try {
            String objectPrefix = fileId.toString() + "/";
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(objectPrefix)
                            .build()
            );
            for (Result<Item> result : results) {
                Item item = result.get();
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(item.objectName())
                                .build()
                );
                log.info(SUCCESS_DELETE_CHUNK_MESSAGE, item.objectName());
            }
            log.info(SUCCESS_DELETE_FILE_MESSAGE, fileId);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(ERROR_DELETE_MESSAGE, fileId, e.toString());
            throw new RuntimeException(String.format(RUNTIME_DELETE_EXCEPTION_MESSAGE, fileId, e.toString()), e);
        }
    }

    @Override
    public InputStream assembleVideo(UUID fileId) {
        if (fileId == null) {
            throw new IllegalArgumentException(NULL_FILE_ID_EXCEPTION_MESSAGE);
        }
        String objectPrefix = fileId.toString() + "/";
        List<InputStream> chunkStreams = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(objectPrefix)
                            .build()
            );
            for (Result<Item> result : results) {
                Item item = result.get();
                InputStream chunkStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(item.objectName())
                                .build()
                );
                chunkStreams.add(chunkStream);
            }
            return new SequenceInputStream(Collections.enumeration(chunkStreams));
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(ERROR_DELETE_MESSAGE, fileId, e.toString());
            throw new RuntimeException(String.format(RUNTIME_DELETE_EXCEPTION_MESSAGE, fileId, e.toString()), e);
        } finally {
            for (InputStream is : chunkStreams) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.warn("Failed to close chunk input stream for file {}: {}", fileId, e.toString());
                }
            }
        }
    }

    public String prepareVideoForStreaming(UUID fileId) {
        if (fileId == null) {
            throw new IllegalArgumentException(NULL_FILE_ID_EXCEPTION_MESSAGE);
        }
        String streamFilePath = fileId + "/stream.m3u8";
        try (InputStream inputStream = assembleVideo(fileId)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(streamFilePath)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType("application/vnd.apple.mpegurl")
                            .build()
            );
            log.info("Successfully prepared streaming file for {}", fileId);
            return streamFilePath;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error occurred while preparing streaming file for {}: {}", fileId, e.toString());
            throw new RuntimeException(String.format("Could not prepare streaming file for %s: %s", fileId, e.toString()), e);
        }
    }
    
    
}