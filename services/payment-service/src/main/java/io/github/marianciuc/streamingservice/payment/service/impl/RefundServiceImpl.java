/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RefundServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import io.github.marianciuc.streamingservice.payment.entity.Transaction;
import io.github.marianciuc.streamingservice.payment.enums.RefundStatus;
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
        Transaction transaction = this.transactionService.findTransactionEntity(transactionId);

        io.github.marianciuc.streamingservice.payment.entity.Refund refund =
                io.github.marianciuc.streamingservice.payment.entity.Refund.builder()
                        .status(RefundStatus.SUCCESS)
                        .currency(transaction.getCurrency())
                        .transaction(transaction)
                        .build();

        RefundCreateParams params =
                RefundCreateParams.builder().setPaymentIntent(transaction.getStripePaymentIntentId()).build();

        try {
            Refund stripeRefund = Refund.create(params);
            refund.setStripeRefundId(stripeRefund.getId());
            refund.setAmount(stripeRefund.getAmount());
            refund.setStatus(RefundStatus.SUCCESS);
        } catch (StripeException e) {
            refund.setStatus(RefundStatus.FAILED);
            throw new RuntimeException(e);
        } finally {
            this.repository.save(refund);
        }
    }
}
