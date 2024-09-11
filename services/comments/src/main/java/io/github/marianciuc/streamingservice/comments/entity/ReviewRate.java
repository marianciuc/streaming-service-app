/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRate.java
 *
 */

package io.github.marianciuc.streamingservice.comments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "review_rates")
public class ReviewRate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Enumerated(EnumType.STRING)
    @Column(name = "rate_type")
    private ReviewRateType reviewRateType;
}
