package com.mv.streamingservice.payment.dto;

import com.mv.streamingservice.payment.entity.Currency;

public record ChargeRequest(
        Currency currency,
        String description,
        String stripeEmail,
        String stripeToken
) {
}
