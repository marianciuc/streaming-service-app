/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EmailVerificationService.java
 *
 */

package io.github.marianciuc.streamingservice.customer.services;

import io.github.marianciuc.streamingservice.customer.exceptions.VerificationCodeException;

/**
 * Service interface for managing email verification processes.
 */
public interface EmailVerificationService {

    /**
     * Sends an email containing a verification code to the specified email address.
     *
     * @param email the email address to which the verification email will be sent.
     */
    void sendVerificationEmail(String email);

    /**
     * Verifies the provided email verification code and returns the corresponding email address.
     *
     * @param verifyCode the email verification code to validate.
     * @return the email address associated with the provided verification code.
     * @throws VerificationCodeException if the verification code is invalid or expired.
     */
    String verifyEmail(String verifyCode) throws VerificationCodeException;
}
