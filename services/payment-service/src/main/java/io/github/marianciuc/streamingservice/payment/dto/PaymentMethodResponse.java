package io.github.marianciuc.streamingservice.payment.dto;

import io.github.marianciuc.streamingservice.payment.entity.PaymentMethodStatus;

import java.util.UUID;

public record PaymentMethodResponse(
        String name,
        String cardBrand,
        UUID id,
        String cardLast4,
        PaymentMethodStatus paymentMethodStatus
) {
}
