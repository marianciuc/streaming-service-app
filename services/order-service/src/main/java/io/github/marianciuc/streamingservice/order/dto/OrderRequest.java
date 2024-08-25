package io.github.marianciuc.streamingservice.order.dto;

import java.util.UUID;

public record OrderRequest(
        UUID userId,
        UUID subscriptionId
) {
}
