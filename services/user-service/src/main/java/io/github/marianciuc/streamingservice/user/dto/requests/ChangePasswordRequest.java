/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ChangePasswordRequest.java
 *
 */

package io.github.marianciuc.streamingservice.user.dto.requests;

import io.github.marianciuc.streamingservice.user.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record ChangePasswordRequest(

        @NotBlank(message = "Old password must not be blank")
        String oldPassword,

        @NotBlank(message = "New password must not be blank")
        @ValidPassword
        String newPassword
) {
}
