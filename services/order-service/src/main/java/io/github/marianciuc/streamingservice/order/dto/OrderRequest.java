package com.mv.streamingservice.order.dto;

import java.util.UUID;

public record OrderRequest(
        UUID userId,
        UUID subscriptionId
) {
}
