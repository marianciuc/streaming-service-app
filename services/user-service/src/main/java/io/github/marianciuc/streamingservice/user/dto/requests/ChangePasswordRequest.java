/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChangePasswordRequest.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.requests;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
