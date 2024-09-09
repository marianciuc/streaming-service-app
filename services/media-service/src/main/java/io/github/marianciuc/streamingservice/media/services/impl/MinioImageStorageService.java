/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MinioPhotoStorageService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.exceptions.ImageNotFoundException;
import io.github.marianciuc.streamingservice.media.exceptions.ImageUploadException;
import io.github.marianciuc.streamingservice.media.services.ImageStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioImageStorageService implements ImageStorageService {

    private static final String ERROR_UPLOADING_PHOTO_MSG = "Error while uploading photo: %s";
    private static final String ERROR_GETTING_IMAGE_MSG = "Error while getting image with filename: %s";
    private static final String ERROR_DELETING_IMAGE_MSG = "Error while deleting image with filename: %s";

    private final String bucketName = "images";

    private final MinioClient minioClient;

    @Override
    public String uploadImage(MultipartFile file) throws ImageUploadException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            InputStream is = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(is, is.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return fileName;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            String errorMsg = String.format(ERROR_UPLOADING_PHOTO_MSG, e.getMessage());
            log.error(errorMsg, e);
            throw new ImageUploadException(errorMsg, e);
        }
    }

    @Override
    public InputStream getImage(String fileName) throws ImageNotFoundException {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            String errorMsg = String.format(ERROR_GETTING_IMAGE_MSG, fileName);
            log.error(errorMsg, e);
            throw new ImageNotFoundException(errorMsg, e);
        }
    }

    @Override
    public void deleteImage(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
            log.info("Photo {} deleted successfully from bucket {}", fileName, bucketName);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            String errorMsg = String.format(ERROR_DELETING_IMAGE_MSG, fileName);
            log.error(errorMsg, e);
        }
    }


}