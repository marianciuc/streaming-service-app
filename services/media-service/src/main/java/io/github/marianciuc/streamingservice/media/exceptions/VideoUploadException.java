/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PhotoUploadException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class VideoUploadException extends VideoStorageException {
    public VideoUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoUploadException(String message) {
        super(message);
    }
}