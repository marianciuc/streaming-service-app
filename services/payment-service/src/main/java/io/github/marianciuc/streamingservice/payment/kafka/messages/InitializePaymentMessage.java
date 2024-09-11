/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: InitializePaymentMessage.java
 *
 */

package io.github.marianciuc.streamingservice.payment.kafka.messages;

import io.github.marianciuc.streamingservice.payment.enums.Currency;

import java.util.UUID;

public record InitializePaymentMessage (
        UUID orderId,
        UUID userId,
        Currency currency,
        Long amount
) {
}
