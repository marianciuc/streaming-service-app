/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserSubscriptionMapper.java
 *
 */

package io.github.marianciuc.streamingservice.subscription.mapper;

import io.github.marianciuc.streamingservice.subscription.dto.UserSubscriptionDto;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A component responsible for mapping UserSubscriptions entity to UserSubscriptionDto.
 */
@Component
@RequiredArgsConstructor
public class UserSubscriptionMapper {

    private final SubscriptionMapper subscriptionMapper;

    /**
     * Converts a UserSubscriptions entity to a UserSubscriptionDto.
     *
     * @param entity the UserSubscriptions entity to be converted.
     * @return a UserSubscriptionDto containing the converted data.
     */
    public UserSubscriptionDto toUserSubscriptionDto(UserSubscriptions entity) {
        return new UserSubscriptionDto(
                entity.getId(),
                entity.getUserId(),
                entity.getOrderId(),
                subscriptionMapper.toResponse(entity.getSubscription()),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStatus()
        );
    }
}
