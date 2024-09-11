/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UpdateCardHolderRequest.java
 *
 */

package io.github.marianciuc.streamingservice.payment.dto.requests;

public record UpdateCardHolderRequest(
        String cardHolderName,
        String phoneNumber,
        String email
) {
}
