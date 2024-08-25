package io.github.marianciuc.streamingservice.payment.dto;


import io.github.marianciuc.streamingservice.payment.entity.Currency;

public record ChargeRequest(
        Currency currency,
        String description,
        String stripeEmail,
        String stripeToken
) {
}
