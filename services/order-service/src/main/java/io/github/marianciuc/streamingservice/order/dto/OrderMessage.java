package io.github.marianciuc.streamingservice.order.dto;

import java.util.UUID;

public record OrderMessage(
        UUID orderId
) {
}
