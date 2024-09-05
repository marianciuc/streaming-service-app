/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: InvalidArgumentsException.java
 *
 */

package io.github.marianciuc.streamingservice.content.exceptions;

public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
