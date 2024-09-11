/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Currency.java
 *
 */

package io.github.marianciuc.streamingservice.payment.enums;

import lombok.Getter;

/**
 * Enum representing the currency of the payment. Each currency has a code, a multiplier to convert the amount to the smallest unit and a minimum amount in units.
 * ISO 3166 codes are used for the currency codes.
 */
@Getter
public enum Currency {
    USD("USD", 100L, 50L),
    PLN("PLN", 100L, 200L),
    EUR("EUR", 100L, 50L),
    CAD("CAD", 100L, 50L),
    CHF("CHF", 100L, 50L),
    CZK("CZK", 100L, 1500L),
    DKK("DKK", 100L, 250L),
    JPY("JPY", 1L, 500L);

    private final String currencyCode;
    private final Long smallestUnitMultiplier;
    private final Long minimumAmountInUnits;

    Currency(String currencyCode, Long smallestUnitMultiplier, Long minimumAmountInUnits) {
        this.currencyCode = currencyCode;
        this.smallestUnitMultiplier = smallestUnitMultiplier;
        this.minimumAmountInUnits = minimumAmountInUnits;
    }
}
