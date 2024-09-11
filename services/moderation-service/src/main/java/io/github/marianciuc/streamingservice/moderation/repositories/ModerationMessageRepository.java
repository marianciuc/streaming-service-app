/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ModerationMessageRepository.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.repositories;

import io.github.marianciuc.streamingservice.moderation.entity.ModerationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModerationMessageRepository extends JpaRepository<ModerationMessage, UUID> {
}
