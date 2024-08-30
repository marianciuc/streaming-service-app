/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ImageNotFoundException.java
 *
 */

package io.github.marianciuc.streamingservice.media.exceptions;

public class MediaContentNotFoundException extends RuntimeException {
    public MediaContentNotFoundException(String message) {
        super(message);
    }
}
