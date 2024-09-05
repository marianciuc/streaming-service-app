/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EmailVerificationService.java
 *
 */

package io.github.marianciuc.streamingservice.customer.services;

import io.github.marianciuc.streamingservice.customer.exceptions.VerificationCodeException;

public interface EmailVerificationService {
    void sendVerificationEmail(String email);
    String verifyEmail(String verifyCode) throws VerificationCodeException;
}
