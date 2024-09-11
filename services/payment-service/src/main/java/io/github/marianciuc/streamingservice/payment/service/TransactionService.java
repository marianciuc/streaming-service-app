/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TransactionService.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service;

import io.github.marianciuc.streamingservice.payment.dto.common.TransactionDto;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;
import io.github.marianciuc.streamingservice.payment.kafka.messages.InitializePaymentMessage;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void initializeTransaction(InitializePaymentMessage message);
    TransactionDto findTransaction(UUID transactionId);
    List<TransactionDto> findAllTransactionsByFilters(UUID userId, PaymentStatus paymentStatus, Integer page,
                                                      Integer size);
}