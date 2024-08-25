package com.mv.streamingservice.order.dto;

import java.util.UUID;

public record OrderMessage(
        UUID orderId
) {
}
