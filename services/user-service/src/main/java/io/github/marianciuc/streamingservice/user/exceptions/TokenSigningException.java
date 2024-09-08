/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TokenSigningException.java
 *
 */

package io.github.marianciuc.streamingservice.user.exceptions;

public class TokenSigningException extends RuntimeException {
    public TokenSigningException(String message, Throwable cause) {
        super(message, cause);
    }
}