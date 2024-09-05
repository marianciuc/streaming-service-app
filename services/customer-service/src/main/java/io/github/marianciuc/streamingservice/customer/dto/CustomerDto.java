/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerDataDto.java
 *
 */

package io.github.marianciuc.streamingservice.customer.dto;

import io.github.marianciuc.streamingservice.customer.model.Customer;
import jakarta.validation.constraints.Email;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDto(
        UUID id,
        String theme,
        @Email(message = "Invalid email address") String email,

        @Before("java.time.LocalDate.now()")
        LocalDate birthDate,
        String country,
        String username,
        String profilePicture,
        String preferredLanguage,
        boolean isEmailVerified,
        boolean receiveNewsletter,
        boolean enableNotifications,

        @Before("java.time.LocalDateTime.now()")
        LocalDateTime createdAt,

        @Before("java.time.LocalDateTime.now()")
        LocalDateTime updatedAt
) {
    public static CustomerDto to(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getTheme(),
                customer.getEmail(),
                customer.getBirthDate(),
                customer.getCountry(),
                customer.getUsername(),
                customer.getProfilePicture(),
                customer.getPreferredLanguage(),
                customer.isEmailVerified(),
                customer.isReceiveNewsletter(),
                customer.isEnableNotifications(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
