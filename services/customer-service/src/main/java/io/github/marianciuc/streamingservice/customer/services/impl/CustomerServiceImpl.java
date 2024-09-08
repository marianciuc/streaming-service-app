/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.customer.services.impl;

import io.github.marianciuc.jwtsecurity.entity.JwtUser;
import io.github.marianciuc.streamingservice.customer.dto.CustomerDto;
import io.github.marianciuc.streamingservice.customer.dto.PaginationResponse;
import io.github.marianciuc.streamingservice.customer.exceptions.AccessDeniedException;
import io.github.marianciuc.streamingservice.customer.exceptions.EntityNotFoundException;
import io.github.marianciuc.streamingservice.customer.exceptions.VerificationCodeException;
import io.github.marianciuc.streamingservice.customer.kafka.messages.CreateUserMessage;
import io.github.marianciuc.streamingservice.customer.model.Customer;
import io.github.marianciuc.streamingservice.customer.repository.CustomerRepository;
import io.github.marianciuc.streamingservice.customer.services.CustomerService;
import io.github.marianciuc.streamingservice.customer.services.EmailVerificationService;
import io.github.marianciuc.streamingservice.customer.specifications.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class CustomerServiceImpl implements CustomerService {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private static class Defaults {
        static final String THEME = "light";
        static final String LANGUAGE = "en";
        static final boolean RECEIVE_NEWSLETTER = false;
        static final boolean ENABLE_NOTIFICATIONS = true;
        static final boolean PROFILE_IS_COMPLETED = false;
        static final boolean EMAIL_VERIFIED = false;
    }

    private final CustomerRepository repository;
    private final EmailVerificationService emailVerificationService;

    @Override
    public void createCustomer(CreateUserMessage message) {
        Customer newCustomer = Customer.builder()
                .id(message.id())
                .email(message.email())
                .theme(Defaults.THEME)
                .preferredLanguage(Defaults.LANGUAGE)
                .profilePicture("")
                .country("")
                .birthDate(LocalDate.now())
                .profileIsCompleted(Defaults.PROFILE_IS_COMPLETED)
                .isEmailVerified(Defaults.EMAIL_VERIFIED)
                .receiveNewsletter(Defaults.RECEIVE_NEWSLETTER)
                .enableNotifications(Defaults.ENABLE_NOTIFICATIONS)
                .username(message.username())
                .build();
        log.info("Creating new customer {}", newCustomer);
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
        return repository.findById(customerId).map(CustomerDto::to).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public void updateCustomerDetails(UUID customerId, CustomerDto customerDto, Authentication authentication) {
        Customer existingCustomer = repository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        JwtUser jwtUser = getAuthenticatedUser(authentication);
        checkUserPermissions(jwtUser, existingCustomer);

        if (customerDto.email() != null) {
            existingCustomer.setEmail(customerDto.email());
            existingCustomer.setEmailVerified(false);
        }
        if (customerDto.theme() != null) existingCustomer.setTheme(customerDto.theme());
        if (customerDto.birthDate() != null) existingCustomer.setBirthDate(customerDto.birthDate());
        if (customerDto.country() != null) existingCustomer.setCountry(customerDto.country());
        if (customerDto.profilePicture() != null) existingCustomer.setProfilePicture(customerDto.profilePicture());
        if (customerDto.preferredLanguage() != null) existingCustomer.setPreferredLanguage(customerDto.preferredLanguage());
        if (customerDto.receiveNewsletter() != existingCustomer.isReceiveNewsletter()) existingCustomer.setReceiveNewsletter(customerDto.receiveNewsletter());
        if (customerDto.enableNotifications() != existingCustomer.isEnableNotifications()) existingCustomer.setEnableNotifications(customerDto.enableNotifications());

        repository.save(existingCustomer);
    }

    private JwtUser getAuthenticatedUser(Authentication authentication) {
        try {
            return (JwtUser) authentication.getPrincipal();
        } catch (ClassCastException e) {
            throw new AccessDeniedException("Invalid authentication principal");
        }
    }


    private void checkUserPermissions(JwtUser jwtUser, Customer existingCustomer) {
        if (!jwtUser.getRole().equals(ADMIN_ROLE) && !jwtUser.getId().equals(existingCustomer.getId())) {
            throw new AccessDeniedException("You do not have permission to update this user");
        }
    }
}
