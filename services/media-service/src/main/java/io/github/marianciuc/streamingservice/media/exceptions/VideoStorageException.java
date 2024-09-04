/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoStorageException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class VideoStorageException extends RuntimeException {
    public VideoStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoStorageException(String message) {
        super(message);
    }
}