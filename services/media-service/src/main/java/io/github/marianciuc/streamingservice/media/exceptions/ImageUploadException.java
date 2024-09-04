/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PhotoUploadException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class ImageUploadException extends RuntimeException {
    public ImageUploadException(String message) {
        super(message);
    }

    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
