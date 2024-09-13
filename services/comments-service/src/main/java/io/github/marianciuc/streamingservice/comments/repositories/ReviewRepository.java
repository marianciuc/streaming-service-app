/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRepository.java
 *
 */

package io.github.marianciuc.streamingservice.comments.repositories;

import io.github.marianciuc.streamingservice.comments.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
