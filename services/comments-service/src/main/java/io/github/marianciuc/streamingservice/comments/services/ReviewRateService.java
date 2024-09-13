/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ReviewRateService.java
 *
 */

package io.github.marianciuc.streamingservice.comments.services;

import java.util.UUID;

public interface ReviewRateService {
    void rateReview(UUID id, Integer rate);
}
