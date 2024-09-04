/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoAssembleException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class VideoAssembleException extends VideoStorageException {
    public VideoAssembleException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoAssembleException(String message) {
        super(message);
    }
}