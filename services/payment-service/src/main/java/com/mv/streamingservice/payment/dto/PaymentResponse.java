package com.mv.streamingservice.payment.dto;

import java.util.UUID;

public record PaymentResponse(
        String name,
        String cardBrand,
        UUID id,
        String cardLast4
) {
}
