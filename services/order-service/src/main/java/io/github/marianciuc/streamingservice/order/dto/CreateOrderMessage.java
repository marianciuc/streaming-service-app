package io.github.marianciuc.streamingservice.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderMessage(
        UUID userId,
        UUID subscriptionId,
        BigDecimal price
) {
}
