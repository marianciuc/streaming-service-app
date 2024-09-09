/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MinioVideoStorageService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.exceptions.VideoAssembleException;
import io.github.marianciuc.streamingservice.media.exceptions.VideoDeleteException;
import io.github.marianciuc.streamingservice.media.exceptions.VideoStorageException;
import io.github.marianciuc.streamingservice.media.exceptions.VideoUploadException;
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

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioVideoStorageService implements VideoStorageService {

    @Value("${minio.bucket.name}")
    private String bucketName;

    private final MinioClient minioClient;

    private static final String NULL_ARGUMENT_EXCEPTION_MESSAGE = "Chunk, chunkNumber, and fileId must not be null";
    private static final String ERROR_UPLOAD_MESSAGE = "Error occurred while uploading chunk {} for file {}: {}";
    private static final String NULL_FILE_ID_EXCEPTION_MESSAGE = "fileId must not be null";
    private static final String SUCCESS_DELETE_CHUNK_MESSAGE = "Successfully deleted chunk {}";
    private static final String SUCCESS_DELETE_FILE_MESSAGE = "Successfully deleted all chunks for file {}";
    private static final String ERROR_DELETE_MESSAGE = "Error occurred while deleting file {}: {}";
    private static final String ERROR_ASSEMBLE_MESSAGE = "Error occurred while assembling file {}: {}";

    @Override
    public void uploadVideoChunk(MultipartFile chunk, Integer chunkNumber, UUID id) {
        if (chunk == null || chunkNumber == null || id == null) {
            throw new VideoUploadException(NULL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        try (InputStream inputStream = chunk.getInputStream()) {
            String objectName = id + "/chunk" + chunkNumber;
            uploadFile(objectName, inputStream, chunk.getSize(), chunk.getContentType());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new VideoUploadException(format(ERROR_UPLOAD_MESSAGE, chunkNumber, id, e), e);
        }
    }

    public void uploadFile(String objectName, InputStream inputStream,
                           long size, String contentType) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, size, -1)
                .contentType(contentType)
                .build()
        );
    }

    @Override
    public void deleteVideo(UUID id) {
        if (id == null) throw new IllegalArgumentException(NULL_FILE_ID_EXCEPTION_MESSAGE);
        try {
            String objectPrefix = id + "/";
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(objectPrefix).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                log.info(SUCCESS_DELETE_CHUNK_MESSAGE, item.objectName());
            }
            log.info(SUCCESS_DELETE_FILE_MESSAGE, id);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new VideoDeleteException(format(ERROR_DELETE_MESSAGE, id, e), e);
        }
    }

    @Override
    public InputStream assembleVideo(UUID id) {
        if (id == null) throw new IllegalArgumentException(NULL_FILE_ID_EXCEPTION_MESSAGE);

        String objectPrefix = id + "/";
        List<InputStream> chunkStreams = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(objectPrefix).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                InputStream chunkStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                chunkStreams.add(chunkStream);
            }
            return new SequenceInputStream(Collections.enumeration(chunkStreams));
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            cleanUpStreams(chunkStreams, id);
            throw new VideoAssembleException(format(ERROR_ASSEMBLE_MESSAGE, id, e), e);
        }
    }

    private void cleanUpStreams(List<InputStream> chunkStreams, UUID id) {
        for (InputStream is : chunkStreams) {
            try {
                is.close();
            } catch (IOException e) {
                log.warn("Failed to close chunk input stream for file {}: {}", id, e.toString());
            }
        }
    }

    @Override
    public InputStream getFileInputStream(String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new VideoStorageException(e.getMessage(), e);
        }
    }
}