/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerDataDto.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String theme,
        LocalDate birthDate,
        String country,
        String username,
        String profilePicture,
        String preferredLanguage,
        boolean isEmailVerified,
        boolean receiveNewsletter,
        boolean enableNotifications,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
