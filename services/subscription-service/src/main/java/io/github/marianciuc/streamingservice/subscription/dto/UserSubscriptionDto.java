/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserSubscriptionDto.java
 *
 */

package io.github.marianciuc.streamingservice.subscription.dto;

import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;

import java.time.LocalDate;
import java.util.UUID;

public record UserSubscriptionDto(
        UUID id,
        UUID userId,
        UUID orderId,
        SubscriptionResponse subscription,
        LocalDate startDate,
        LocalDate endDate,
        SubscriptionStatus status
) {
}
