/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerDataDto.java
 *
 */

package io.github.marianciuc.streamingservice.customer.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDataDto(
        UUID id,
        String theme,
        String email,
        LocalDate birthDate,
        String country,
        String username,
        String profilePicture,
        String preferredLanguage,
        boolean isEmailVerified,
        boolean receiveNewsletter,
        boolean enableNotifications,
        LocalDateTime createdAt
) {
}
