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
import io.github.marianciuc.streamingservice.customer.dto.CustomerDto;
import io.github.marianciuc.streamingservice.customer.dto.PaginationResponse;
import io.github.marianciuc.streamingservice.customer.exceptions.VerificationCodeException;
import io.github.marianciuc.streamingservice.customer.model.Customer;
import io.github.marianciuc.streamingservice.customer.repository.CustomerRepository;
import io.github.marianciuc.streamingservice.customer.specifications.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class that handles operations related to customers, such as creating a customer,
 * starting email verification, verifying email codes, and finding customers based on filters.
 */
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


    /**
     * Creates a new customer and saves it to the repository.
     *
     * @param message Contains data for creating a new customer including id, email, and username.
     */
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

    public PaginationResponse<List<CustomerDto>> findAllByFilter(Integer page, Integer pageSize, String country, String email, String id, boolean isEmailVerified, String username) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Specification<Customer> spec = Specification.where(
                        Optional.ofNullable(country).map(CustomerSpecification::hasCountry).orElse(null))
                .and(Optional.ofNullable(email).map(CustomerSpecification::emailStartsWith).orElse(null))
                .and(Optional.ofNullable(username).map(CustomerSpecification::usernameStartsWith).orElse(null))
                .and(CustomerSpecification.isEmailVerified(isEmailVerified));

        Page<Customer> customerPage = repository.findAll(spec, pageable);

        return new PaginationResponse<>(
                customerPage.getTotalPages(),
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getContent().stream().map(CustomerDto::to).toList()
        );
    }

    public CustomerDto findById(UUID customerId) {
        return repository.findById(customerId).map(CustomerDto::to).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
