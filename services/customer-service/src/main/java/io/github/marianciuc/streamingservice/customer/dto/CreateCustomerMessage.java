/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateCustomerMessage.java
 *
 */

package io.github.marianciuc.streamingservice.customer.dto;

import java.util.UUID;

public record CreateCustomerMessage (
        UUID id,
        String email,
        String username
) {
}
