/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RateService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import io.github.marianciuc.streamingservice.content.exceptions.NotFoundException;

import java.util.UUID;

/**
 * The service interface for the rate entity
 */
public interface RateService {

    /**
     * Rates the content with the given UUID with the given rate
     * The rate must be between 1 and 5
     * If the user has already rated the content, the previous rate will be replaced
     * If the user has not rated the content, the new rate will be added
     * @param id  the UUID of the content to be rated
     * @param rate the rate to be given to the content
     * @throws IllegalArgumentException if the rate is not between 1 and 5
     * @throws NotFoundException if the content with the given UUID is not found
     */
    void rateContent(UUID id, Integer rate);
}
