/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CardHolderRepository.java
 *
 */

package io.github.marianciuc.streamingservice.payment.repository;

import io.github.marianciuc.streamingservice.payment.entity.CardHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardHolderRepository extends JpaRepository<CardHolder, UUID> {
    boolean existsCardHolderByUserId(UUID id);
}
