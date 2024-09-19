/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PhotoStorageService.java
 *
 */

package io.github.marianciuc.streamingservice.media.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Service interface for managing images in storage.
 */
public interface ImageStorageService {

    /**
     * Uploads a photo. Previously file must be validated by {@code @Image} annotation.
     *
     * @param file the photo to be uploaded
     * @return the unique identifier (e.g., filename) of the uploaded photo
     */
    String upload(MultipartFile file);

    /**
     * Retrieves a image input stream.
     *
     * @param fileName the unique identifier of the photo
     * @return an InputStream to read the photo content
     */
    InputStream find(String fileName);

    /**
     * Deletes a photo.
     *
     * @param fileName the unique identifier of the photo
     * @throws org.springframework.security.access.AccessDeniedException if the user is not authorized to delete the
     * photo or the photo publisher is different from the current user and the current user is not an admin
     */
    void delete(String fileName);
}
