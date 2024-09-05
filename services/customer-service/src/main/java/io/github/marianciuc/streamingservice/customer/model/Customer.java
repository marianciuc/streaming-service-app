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
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Entity class representing a customer in the Streaming Service App.
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    /**
     * The unique identifier of the customer.
     */
    @Id
    private UUID id;

    /**
     * The email address of the customer.
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * The first name of the customer.
     */
    @Column(name = "theme")
    private String theme;

    /**
     * The birth date of the customer.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * The country of the customer.
     */
    @Column(name = "country")
    private String country;

    /**
     * The username of the customer.
     */
    @Column(name = "username", unique = true)
    private String username;

    /**
     * The profile picture URL of the customer.
     */
    @Column(name = "profile_picture")
    private String profilePicture;

    /**
     * The preferred language of the customer.
     */
    @Column(name = "preferred_language")
    private String preferredLanguage;

    /**
     * Indicates whether the customer's email is verified.
     */
    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    /**
     * Indicates whether the customer's profile is completed.
     */
    @Column(name = "profile_is_completed")
    private boolean profileIsCompleted;

    /**
     * Indicates whether the customer has subscribed to receive newsletters.
     */
    @Column(name = "receive_newsletter")
    private boolean receiveNewsletter;

    /**
     * Indicates whether the customer has enabled notifications.
     */
    @Column(name = "enable_notifications")
    private boolean enableNotifications;

    /**
     * The timestamp when the customer was created.
     */
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the customer was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
