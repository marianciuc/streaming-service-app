/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: KafkaPaymentConsumer.java
 *
 */

package io.github.marianciuc.streamingservice.payment.kafka;

import io.github.marianciuc.streamingservice.payment.kafka.messages.InitializePaymentMessage;
import io.github.marianciuc.streamingservice.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPaymentConsumer {

    private final TransactionService transactionService;

    @KafkaListener(topics = "start-payment-processing" , groupId = "spring.kafka.consumer.group-id")
    public void consume(InitializePaymentMessage message) {
        System.out.println("Consumed message: " + message);
        transactionService.initializeTransaction(message);
    }
}
