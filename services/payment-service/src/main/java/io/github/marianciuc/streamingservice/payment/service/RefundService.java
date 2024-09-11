/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefundService.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service;

import java.util.UUID;

public interface RefundService {
    void processRefund(UUID transactionId);
}
