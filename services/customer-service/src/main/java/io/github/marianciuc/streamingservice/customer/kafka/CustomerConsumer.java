/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerConcumer.java
 *
 */

package io.github.marianciuc.streamingservice.customer.kafka;

import io.github.marianciuc.streamingservice.customer.dto.CreateCustomerMessage;
import io.github.marianciuc.streamingservice.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerConsumer {

    private final CustomerService customerService;

    @KafkaListener(topics = "customer-creation", groupId = "customers")
    public void consume(CreateCustomerMessage message) {
        System.out.println("Consumed message: " + message);
        customerService.createCustomer(message);
    }
}
