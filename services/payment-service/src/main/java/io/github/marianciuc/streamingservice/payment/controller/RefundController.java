/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefundController.java
 *
 */

package io.github.marianciuc.streamingservice.payment.controller;

import io.github.marianciuc.streamingservice.payment.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/v1/payments/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/{transaction-id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    private ResponseEntity<Void> refundPayment(@PathVariable("transaction-id") UUID transactionId) {
        refundService.processRefund(transactionId);
        return ResponseEntity.ok().build();
    }
}
