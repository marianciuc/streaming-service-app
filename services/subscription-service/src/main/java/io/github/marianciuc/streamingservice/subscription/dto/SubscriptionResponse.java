package io.github.marianciuc.streamingservice.subscription.dto;


import io.github.marianciuc.streamingservice.subscription.entity.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record SubscriptionResponse(
        UUID id,
        String name,
        String description,
        Integer allowedActiveSessions,
        Integer durationInDays,
        Set<ResolutionDto> resolutions,
        BigDecimal price,
        Boolean isTemporary,
        UUID nextSubscriptionId,
        LocalDateTime updatedAt,
        RecordStatus recordStatus,
        LocalDateTime createdAt
) { }
