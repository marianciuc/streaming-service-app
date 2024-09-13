/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TransactionDto.java
 *
 */

package io.github.marianciuc.streamingservice.payment.dto.common;

import io.github.marianciuc.streamingservice.payment.entity.Transaction;
import io.github.marianciuc.streamingservice.payment.enums.Currency;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDto(
        UUID id,
        UUID orderId,
        UUID userId,
        Currency currency,
        BigDecimal amount,
        String failureMessage,
        PaymentStatus paymentStatus,
        LocalDateTime createdAt
) {
    public static TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getOrderId(),
                transaction.getCardHolder().getUserId(),
                transaction.getCurrency(),
                BigDecimal.valueOf(transaction.getAmount()).divide(BigDecimal.valueOf(transaction.getCurrency().getSmallestUnitMultiplier())),
                transaction.getFailureMessage(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );
    }
}
