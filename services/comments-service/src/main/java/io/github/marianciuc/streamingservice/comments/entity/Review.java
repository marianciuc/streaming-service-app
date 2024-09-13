/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Review.java
 *
 */

package io.github.marianciuc.streamingservice.comments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "parent_review_id")
    private Review parentReview;

    private UUID userId;

    private UUID contentId;

    private String content;

    private Boolean isDeleted;

    private ModerationStatus moderationStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer likes = 0;

    private Integer dislikes = 0;
}
