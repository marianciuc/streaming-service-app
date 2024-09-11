/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRateRepository.java
 *
 */

package io.github.marianciuc.streamingservice.comments.repositories;

import io.github.marianciuc.streamingservice.comments.entity.ReviewRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRateRepository extends JpaRepository<ReviewRate, UUID> {
}