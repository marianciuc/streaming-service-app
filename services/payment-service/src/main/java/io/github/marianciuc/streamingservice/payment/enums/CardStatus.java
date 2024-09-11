/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CardStatus.java
 *
 */

package io.github.marianciuc.streamingservice.payment.enums;


import lombok.Getter;

@Getter
public enum CardStatus {

    ACTIVE("active"),
    INACTIVE("inactive");

    private final String status;

    CardStatus(String status) {
        this.status = status;
    }
}
