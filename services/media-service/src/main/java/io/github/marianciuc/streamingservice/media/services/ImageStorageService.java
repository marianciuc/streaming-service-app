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

public interface ImageStorageService {

    /**
     * Uploads a photo.
     *
     * @param file the photo to be uploaded
     * @return the unique identifier (e.g., filename) of the uploaded photo
     */
    String uploadPhoto(MultipartFile file);

    /**
     * Retrieves a photo.
     *
     * @param fileName the unique identifier of the photo
     * @return an InputStream to read the photo content
     */
    InputStream getPhoto(String fileName);

    /**
     * Deletes a photo.
     *
     * @param fileName the unique identifier of the photo
     */
    void deletePhoto(String fileName);
}
