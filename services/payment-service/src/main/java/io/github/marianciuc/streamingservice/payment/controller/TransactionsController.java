/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TransactionsController.java
 *
 */

package io.github.marianciuc.streamingservice.payment.controller;

import io.github.marianciuc.streamingservice.payment.dto.common.TransactionDto;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;
import io.github.marianciuc.streamingservice.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("/api/v1/payments/transactions")
@RequiredArgsConstructor
public class TransactionsController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "asc") String sort,
            @RequestParam(value = "status", required = false) PaymentStatus status,
            @RequestParam(value = "userId", required = false) UUID userId
    ) {
        return ResponseEntity.ok(transactionService.getTransactions(page, size, sort, status, userId));
    }
}
