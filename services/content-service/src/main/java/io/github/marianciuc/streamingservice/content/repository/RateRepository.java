/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RateRepository.java
 *
 */

package io.github.marianciuc.streamingservice.content.repository;

import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RateRepository extends JpaRepository<Rate, UUID> {
    Optional<Rate> findByUserIdAndContent(UUID userId, Content content);
}
