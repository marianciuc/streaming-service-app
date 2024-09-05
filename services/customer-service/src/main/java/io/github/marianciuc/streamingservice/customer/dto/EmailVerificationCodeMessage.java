/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EmailVerificationCodeMessage.java
 *
 */

package io.github.marianciuc.streamingservice.customer.dto;

public record EmailVerificationCodeMessage(
        String email,
        String verificationCode,
        int codeExpirationTimeInMinutes
) {
}
