/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PaymentStatusMessage.java
 *
 */

package io.github.marianciuc.streamingservice.payment.kafka.messages;

import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;

import java.util.UUID;

public record PaymentStatusMessage(
        UUID userId,
        UUID orderId,
        PaymentStatus paymentStatus,
        String message
) {
}
