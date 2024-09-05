/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Customer.java
 *
 */

package io.github.marianciuc.streamingservice.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    private UUID id;

    @Column(name = "email", unique = true)
    private String email;

    private String theme;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "country")
    private String country;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "preferred_language")
    private String preferredLanguage;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "profile_is_completed")
    private boolean profileIsCompleted;

    @Column(name = "receive_newsletter")
    private boolean receiveNewsletter;

    @Column(name = "enable_notifications")
    private boolean enableNotifications;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
