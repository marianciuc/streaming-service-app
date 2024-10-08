/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MediaServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.entity.Image;
import io.github.marianciuc.streamingservice.media.exceptions.ImageNotFoundException;
import io.github.marianciuc.streamingservice.media.exceptions.ImageUploadException;
import io.github.marianciuc.streamingservice.media.services.ImageStorageService;
import io.github.marianciuc.streamingservice.media.dto.ImageDto;
import io.github.marianciuc.streamingservice.media.repository.ImageRepository;
import io.github.marianciuc.streamingservice.media.services.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final String FORBIDDEN_MSG = "You are not authorized to upload photos.";
    private static final String IMAGE_NOT_FOUND_MSG = "Image not found with ID: %s";
    private static final String UPLOAD_FAILED_MSG = "Failed to upload photo: %s";
    private static final String ERROR_READING_IMAGE_MSG = "Error reading image content with filename: %s";
    private static final String ERROR_DELETING_IMAGE_MSG = "Error deleting image with ID: %s, filename: %s";

    private final ImageRepository repository;
    private final ImageStorageService imageStorageService;

    @Override
    @Transactional
    public UUID upload(MultipartFile file) {
        try {
            String url = imageStorageService.upload(file);
            Image image = Image.builder()
                    .contentLength(file.getSize())
                    .contentType(file.getContentType())
                    .createdAt(LocalDateTime.now())
                    .fileName(url)
                    .build();
            return repository.save(image).getId();
        } catch (ImageUploadException e) {
            String errorMsg = String.format(UPLOAD_FAILED_MSG, e.getMessage());
            log.error(errorMsg, e);
            throw new ImageUploadException(errorMsg);
        }
    }

    @Override
    public ImageDto find(UUID id) {
        Image image = repository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, id)));
        String fileName = image.getFileName();
        try (InputStream photoInputStream = imageStorageService.find(fileName)) {
            ByteArrayResource byteArrayResource = new ByteArrayResource(photoInputStream.readAllBytes());
            return new ImageDto(byteArrayResource, image.getContentType());
        } catch (IOException e) {
            String errorMsg = String.format(ERROR_READING_IMAGE_MSG, fileName);
            log.error(errorMsg, e);
            throw new ImageNotFoundException(errorMsg);
        }
    }

    @Override
    public void delete(UUID id) {
        Image image = repository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, id)));
        String fileName = image.getFileName();
        try {
            imageStorageService.delete(fileName);
            repository.delete(image);
        } catch (Exception e) {
            String errorMsg = String.format(ERROR_DELETING_IMAGE_MSG, id, fileName);
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg);
        }
    }
}