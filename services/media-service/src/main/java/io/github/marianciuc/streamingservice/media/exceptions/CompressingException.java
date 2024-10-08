/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CompressingException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class CompressingException extends VideoStorageException {
    public CompressingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressingException(String message) {
        super(message);
    }
}