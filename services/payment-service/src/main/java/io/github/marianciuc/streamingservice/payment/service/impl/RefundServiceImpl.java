/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefundServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service.impl;

import io.github.marianciuc.streamingservice.payment.dto.common.TransactionDto;
import io.github.marianciuc.streamingservice.payment.repository.RefundRepository;
import io.github.marianciuc.streamingservice.payment.service.RefundService;
import io.github.marianciuc.streamingservice.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository repository;
    private final TransactionService transactionService;

    @Override
    public void processRefund(UUID transactionId) {
        TransactionDto transaction = transactionService.findTransaction(transactionId);
    }
}
