/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: VideoDeleteException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class VideoDeleteException extends VideoStorageException {
    public VideoDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoDeleteException(String message) {
        super(message);
    }
}