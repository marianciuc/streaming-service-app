/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TransactionServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import io.github.marianciuc.streamingservice.payment.dto.common.CardHolderDto;
import io.github.marianciuc.streamingservice.payment.dto.common.TransactionDto;
import io.github.marianciuc.streamingservice.payment.entity.Transaction;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;
import io.github.marianciuc.streamingservice.payment.kafka.PaymentKafkaProducer;
import io.github.marianciuc.streamingservice.payment.kafka.messages.InitializePaymentMessage;
import io.github.marianciuc.streamingservice.payment.repository.TransactionRepository;
import io.github.marianciuc.streamingservice.payment.service.CardHolderService;
import io.github.marianciuc.streamingservice.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final CardHolderService cardHolderService;
    private final PaymentKafkaProducer paymentKafkaProducer;

    @Override
    public void initializeTransaction(InitializePaymentMessage message) {
        CardHolderDto cardHolderDto = cardHolderService.getCardHolder(message.userId());

        Transaction transaction = Transaction.builder()
                .amount(message.amount())
                .currency(message.currency())
                .orderId(message.orderId())
                .status(PaymentStatus.PENDING)
                .cardHolder(CardHolderDto.toDto(cardHolderDto))
                .build();

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(message.amount() * message.currency().getSmallestUnitMultiplier())
                        .setCurrency(message.currency().name().toLowerCase())
                        .setConfirm(true).setAutomaticPaymentMethods(PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true).setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER).build())
                        .setCustomer(cardHolderDto.stripeCustomerId())
                        .build();
        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            transaction.setStripePaymentIntentId(paymentIntent.getId());
            transaction.setStatus(PaymentStatus.SUCCESS);
//            paymentKafkaProducer.sendInitializePaymentMessage(new PaymentStatusMessage(
//                    message.userId(), message.orderId(), PaymentStatus.SUCCESS, "Payment successful"
//            ));
        } catch (StripeException e) {
            transaction.setStatus(PaymentStatus.FAILED);
            transaction.setFailureMessage(e.getMessage());
//            paymentKafkaProducer.sendInitializePaymentMessage(new PaymentStatusMessage(
//                    message.userId(), message.orderId(), PaymentStatus.FAILED, e.getMessage()
//            ));
            throw new RuntimeException(e);
        } finally {
            repository.save(transaction);
        }
    }

    @Override
    public TransactionDto findTransaction(UUID transactionId) {
        return null;
    }

    @Override
    public List<TransactionDto> findAllTransactionsByFilters(UUID userId, PaymentStatus paymentStatus, Integer page, Integer size) {
        return List.of();
    }
}
