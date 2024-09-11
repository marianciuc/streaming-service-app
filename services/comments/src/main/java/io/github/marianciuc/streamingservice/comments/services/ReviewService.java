/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewService.java
 *
 */

package io.github.marianciuc.streamingservice.comments.services;

import io.github.marianciuc.streamingservice.comments.dto.ReviewDto;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewDto addReview(ReviewDto reviewDto);
    ReviewDto getReview(UUID id);
    void deleteReview(UUID id);
    List<ReviewDto> getReviewsByFilter(UUID contentId, UUID userId, Boolean approved);
}
