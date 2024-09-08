/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CredentialsRequest.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.requests;

public record CredentialsRequest(
        String login,
        String password
) {
}
