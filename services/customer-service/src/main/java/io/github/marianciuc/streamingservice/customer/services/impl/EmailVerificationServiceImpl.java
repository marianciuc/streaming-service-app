/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: EmailVerificationServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.customer.services.impl;

import io.github.marianciuc.streamingservice.customer.dto.EmailVerificationCodeMessage;
import io.github.marianciuc.streamingservice.customer.exceptions.VerificationCodeException;
import io.github.marianciuc.streamingservice.customer.kafka.EmailNotificationProducer;
import io.github.marianciuc.streamingservice.customer.services.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private static final String VERIFICATION_CODE_PREFIX = "verification_code:";
    private static final int VERIFICATION_CODE_LENGTH = 6;
    private static final int VERIFICATION_CODE_EXPIRATION_IN_MINUTES = 10;

    private final EmailNotificationProducer emailNotificationProducer;
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendVerificationEmail(String email) {
        String code = generateVerificationCode();
        saveVerificationCode(email, code);
        emailNotificationProducer.sendEmailNotification(new EmailVerificationCodeMessage(email, code, VERIFICATION_CODE_EXPIRATION_IN_MINUTES));
    }

    @Override
    public String verifyEmail(String verifyCode) throws VerificationCodeException {
        String key = VERIFICATION_CODE_PREFIX + verifyCode;
        String storedEmail = redisTemplate.opsForValue().get(key);

        if (storedEmail == null) {
            log.error("Verification code {} is invalid or expired", verifyCode);
            throw new VerificationCodeException("Invalid or expired verification code");
        } else {
            redisTemplate.delete(key);
            return storedEmail;
        }

    }

    private void saveVerificationCode(String email, String code) {
        redisTemplate.opsForValue().set(getKey(code), email, VERIFICATION_CODE_EXPIRATION_IN_MINUTES, TimeUnit.MINUTES);
    }


    private String generateVerificationCode() {
        Random rand = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) code.append(rand.nextInt(10));
        return code.toString();
    }

    private String getKey(String code) {
        return VERIFICATION_CODE_PREFIX + code;
    }
}
