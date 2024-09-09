/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MediaService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ImageDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Service interface for managing images.
 */
public interface ImageService {

    /**
     * Retrieves an image by its ID.
     *
     * @param id The UUID of the image.
     * @return An ImageDto containing the image data.
     */
    ImageDto getImage(UUID id);

    /**
     * Uploads a new image.
     *
     * @param file The image file to upload.
     * @param authentication The authentication information of the user uploading the image.
     * @return The UUID of the uploaded image.
     */
    UUID uploadImage(MultipartFile file);

    /**
     * Deletes an image by its ID.
     *
     * @param id The UUID of the image to delete.
     */
    void deleteImage(UUID id);
}
