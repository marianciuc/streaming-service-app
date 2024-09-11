/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CardHolderService.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service;

import io.github.marianciuc.streamingservice.payment.dto.common.CardHolderDto;
import io.github.marianciuc.streamingservice.payment.dto.requests.CreateCartHolderRequest;

import java.util.UUID;

public interface CardHolderService {
    CardHolderDto createCardHolder(CreateCartHolderRequest request);
    CardHolderDto getCardHolder(UUID cardHolderId);
}
