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
import io.github.marianciuc.streamingservice.payment.dto.common.TransactionDto;
import io.github.marianciuc.streamingservice.payment.dto.responses.PaginationResponse;
import io.github.marianciuc.streamingservice.payment.entity.CardHolder;
import io.github.marianciuc.streamingservice.payment.entity.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.payment.entity.Transaction;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;
import io.github.marianciuc.streamingservice.payment.kafka.PaymentKafkaProducer;
import io.github.marianciuc.streamingservice.payment.kafka.messages.InitializePaymentMessage;
import io.github.marianciuc.streamingservice.payment.repository.TransactionRepository;
import io.github.marianciuc.streamingservice.payment.service.CardHolderService;
import io.github.marianciuc.streamingservice.payment.service.TransactionService;
import io.github.marianciuc.streamingservice.payment.specifications.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
        CardHolder cardHolder = cardHolderService.findCardHolderEntity(message.userId());

        Transaction transaction = Transaction.builder()
                .amount(message.amount())
                .currency(message.currency())
                .orderId(message.orderId())
                .status(PaymentStatus.PENDING)
                .cardHolder(cardHolder)
                .build();

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(message.amount() * message.currency().getSmallestUnitMultiplier())
                        .setCurrency(message.currency().name().toLowerCase())
                        .setConfirm(true).setAutomaticPaymentMethods(PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true).setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER).build())
                        .setCustomer(cardHolder.getStripeCustomerId())
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
    public List<TransactionDto> getTransactions(Integer page, Integer size, String sort, PaymentStatus status, UUID userId) {
        Sort.Direction sortDirection = Sort.Direction.fromString(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "amount"));

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof JWTUserPrincipal jwtUserPrincipal) {
            if (jwtUserPrincipal.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                userId = (userId != null) ? userId : jwtUserPrincipal.getUserId();
            } else {
                userId = jwtUserPrincipal.getUserId();
            }
            Specification<Transaction> specification = TransactionSpecification.buildSpec(status, userId);
            Page<Transaction> transactionPage = repository.findAll(specification, pageable);

            PaginationResponse<List<TransactionDto>> response = new PaginationResponse<>(
                    transactionPage.getTotalPages(),
                    transactionPage.getNumber(),
                    transactionPage.getNumberOfElements(),
                    transactionPage.getContent().stream().map(TransactionDto::toDto).toList()
            );
        }
        throw new AccessDeniedException("Access denied");
    }
}
