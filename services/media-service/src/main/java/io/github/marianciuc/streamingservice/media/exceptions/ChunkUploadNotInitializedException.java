/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChunkUploadNotInitializedException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class ChunkUploadNotInitializedException extends RuntimeException {
    public ChunkUploadNotInitializedException(String message) {
        super(message);
    }

    public ChunkUploadNotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }
}