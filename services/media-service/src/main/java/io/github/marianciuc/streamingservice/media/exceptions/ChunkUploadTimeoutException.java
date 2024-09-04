/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChunkUploadTimeoutException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class ChunkUploadTimeoutException extends RuntimeException {
    public ChunkUploadTimeoutException(String message) {
        super(message);
    }

    public ChunkUploadTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}