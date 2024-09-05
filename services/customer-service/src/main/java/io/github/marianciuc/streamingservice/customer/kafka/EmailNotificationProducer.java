/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EmailNotificationProducer.java
 *
 */

package io.github.marianciuc.streamingservice.customer.kafka;


import io.github.marianciuc.streamingservice.customer.dto.EmailVerificationCodeMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationProducer {

    private KafkaTemplate<String, EmailVerificationCodeMessage> kafkaTemplate;

    public void sendEmailNotification(EmailVerificationCodeMessage message) {
        kafkaTemplate.send("email-verification", message);
    }
}
