package com.mv.streamingservice.payment.dto;

public record PaymentRequest(
        String name,
        String cardNumber,
        String cardBrand,
        String cardExpiryMonth,
        String cardExpiryYear,
        String cardCVV
) {
}
