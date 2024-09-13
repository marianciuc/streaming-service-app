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
import io.github.marianciuc.streamingservice.payment.dto.requests.UpdateCardHolderRequest;
import io.github.marianciuc.streamingservice.payment.entity.CardHolder;

import java.util.UUID;

public interface CardHolderService {
    CardHolderDto createCardHolder(CreateCartHolderRequest request);
    CardHolderDto findCardHolder(UUID cardHolderId);
    void updatePaymentMethod(String token);
    void updateCardHolder(UpdateCardHolderRequest request);
    CardHolder findCardHolderEntity(UUID cardHolderId);
}
