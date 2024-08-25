package io.github.marianciuc.streamingservice.payment.dto;

public record PaymentRequest(
        String name,
        String cardNumber,
        String cardBrand,
        String cardExpiryMonth,
        String cardExpiryYear,
        String cardCVV
) {
}
