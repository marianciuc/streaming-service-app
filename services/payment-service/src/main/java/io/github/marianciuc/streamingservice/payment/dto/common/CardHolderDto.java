/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CardHolderDto.java
 *
 */

package io.github.marianciuc.streamingservice.payment.dto.common;

import io.github.marianciuc.streamingservice.payment.entity.CardHolder;

import java.util.UUID;

public record CardHolderDto(
        UUID id,
        String stripeCustomerId,
        AddressDto address,
        String cardHolderName,
        String phoneNumber,
        String email
) {
    public static CardHolderDto toDto(CardHolder cardHolder) {
        return new CardHolderDto(
                cardHolder.getUserId(),
                cardHolder.getStripeCustomerId(),
                AddressDto.toDto(cardHolder.getAddress()),
                cardHolder.getCardHolderName(),
                cardHolder.getPhoneNumber(),
                cardHolder.getEmail()
        );
    }

    public static CardHolder toDto(CardHolderDto cardHolderDto) {
        return CardHolder.builder().userId(cardHolderDto.id).build();
    }
}
