/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: MediaService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import io.github.marianciuc.streamingservice.media.dto.ImageDto;
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
    ImageDto find(UUID id);

    /**
     * Uploads a photo. Previously file must be validated by {@code @Image} annotation.
     *
     * @param file the photo to be uploaded
     * @return the unique identifier of the uploaded photo
     */
    UUID upload(MultipartFile file);

    /**
     * Deletes a photo.
     *
     * @param id the unique identifier of the photo
     * @throws org.springframework.security.access.AccessDeniedException if the user is not authorized to delete the
     * photo or the photo publisher is different from the current user and the current user is not an admin
     */
    void delete(UUID id);
}
