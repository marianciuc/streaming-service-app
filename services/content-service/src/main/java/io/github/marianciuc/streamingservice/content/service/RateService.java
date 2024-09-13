/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RateService.java
 *
 */

package io.github.marianciuc.streamingservice.content.service;

import java.util.UUID;

public interface RateService {
    void rateContent(UUID id, Integer rate);
}
