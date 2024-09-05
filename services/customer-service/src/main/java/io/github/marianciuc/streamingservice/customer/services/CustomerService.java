/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerService.java
 *
 */

package io.github.marianciuc.streamingservice.customer.services;

import io.github.marianciuc.jwtsecurity.entity.JwtUser;
import io.github.marianciuc.streamingservice.customer.dto.CreateCustomerMessage;
import io.github.marianciuc.streamingservice.customer.exceptions.VerificationCodeException;
import io.github.marianciuc.streamingservice.customer.model.Customer;
import io.github.marianciuc.streamingservice.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private static final String DEFAULT_THEME = "light";
    private static final String DEFAULT_LANGUAGE = "en";
    private static final boolean DEFAULT_RECEIVE_NEWSLETTER = false;
    private static final boolean DEFAULT_ENABLE_NOTIFICATIONS = true;
    private static final boolean DEFAULT_PROFILE_IS_COMPLETED = false;
    private static final boolean DEFAULT_EMAIL_VERIFIED = false;

    private final CustomerRepository repository;
    private final EmailVerificationService emailVerificationService;


    public void createCustomer(CreateCustomerMessage message) {
        Customer newCustomer = Customer.builder()
                .id(message.id())
                .email(message.email())
                .theme(DEFAULT_THEME)
                .preferredLanguage(DEFAULT_LANGUAGE)
                .profileIsCompleted(DEFAULT_PROFILE_IS_COMPLETED)
                .isEmailVerified(DEFAULT_EMAIL_VERIFIED)
                .receiveNewsletter(DEFAULT_RECEIVE_NEWSLETTER)
                .enableNotifications(DEFAULT_ENABLE_NOTIFICATIONS)
                .username(message.username())
                .build();
        repository.save(newCustomer);
    }

    public void startEmailVerification(Authentication authentication) {
        Customer customer = getCustomerFromAuth(authentication);

        if (customer.isEmailVerified()) {
            log.error("Email {} is already verified", customer.getEmail());
            throw new VerificationCodeException("Email is already verified");
        }

        emailVerificationService.sendVerificationEmail(customer.getEmail());
    }

    public void verifyCode(String verifyCode, Authentication authentication) {
        Customer customer = getCustomerFromAuth(authentication);

        try {
            String verifiedEmail = emailVerificationService.verifyEmail(verifyCode);
            if (!verifiedEmail.equals(customer.getEmail())) {
                String errorMsg = String.format("Email %s does not match the authenticated user %s", verifiedEmail, customer.getEmail());
                log.error(errorMsg);
                throw new VerificationCodeException("Invalid verification code");
            }
            customer.setEmailVerified(true);
            repository.save(customer);
        } catch (VerificationCodeException e) {
            log.error("Verification code {} is invalid or expired", verifyCode);
            throw e;
        }
    }

    private Customer getCustomerFromAuth(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return repository.findById(jwtUser.getId()).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
