package com.mv.streamingservice.subscription.dto;

import com.mv.streamingservice.subscription.entity.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionResponse(
        String id,
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
) { }
