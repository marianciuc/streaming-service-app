/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionRepository.java
 *
 */

package io.github.marianciuc.streamingservice.media.repository;

import io.github.marianciuc.streamingservice.media.entity.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResolutionRepository extends JpaRepository<Resolution, UUID> {
}
