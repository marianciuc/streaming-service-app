package io.github.marianciuc.streamingservice.payment.dto;

import java.util.UUID;

public record InitializePaymentRequest(
        UUID orderId
) {
}
