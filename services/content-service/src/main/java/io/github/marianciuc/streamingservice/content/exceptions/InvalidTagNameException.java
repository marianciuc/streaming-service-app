/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: InvalidTagNameException.java
 *
 */

package io.github.marianciuc.streamingservice.content.exceptions;

public class InvalidTagNameException extends IllegalArgumentException {
    public InvalidTagNameException(String message) {
        super(message);
    }
}