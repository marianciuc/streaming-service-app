package com.mv.streamingservice.order.dto;

import com.mv.streamingservice.order.entity.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Subscription(
        UUID id,
        String name,
        String description,
        Integer allowedActiveSessions,
        Integer durationInDays,
        BigDecimal price,
        Boolean isTemporary,
        UUID nextSubscriptionId,
        LocalDateTime updatedAt,
        RecordStatus recordStatus,
        LocalDateTime createdAt
) {
}