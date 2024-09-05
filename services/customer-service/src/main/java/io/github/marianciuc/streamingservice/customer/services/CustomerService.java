/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerService.java
 *
 */

package io.github.marianciuc.streamingservice.customer.services;

import io.github.marianciuc.streamingservice.customer.dto.CreateCustomerMessage;
import io.github.marianciuc.streamingservice.customer.dto.CustomerDto;
import io.github.marianciuc.streamingservice.customer.dto.PaginationResponse;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing customer-related operations.
 */
public interface CustomerService {

    /**
     * Creates a new customer in the system.
     *
     * @param message the message containing the details of the customer to be created.
     */
    void createCustomer(CreateCustomerMessage message);


    /**
     * Initiates the email verification process for a customer based on their authentication details.
     *
     * @param authentication the authentication details of the current user, used to identify the customer.
     */
    void startEmailVerification(Authentication authentication);

    /**
     * Verifies the provided email verification code for the authenticated customer.
     *
     * @param verifyCode the email verification code sent to the customer's email.
     * @param authentication the authentication details of the current user, used to identify the customer.
     */
    void verifyCode(String verifyCode, Authentication authentication);

    /**
     * Finds all customers based on the provided filter criteria.
     *
     * @param page the page number of the results to retrieve.
     * @param pageSize the number of customers to retrieve per page.
     * @param country the country to filter customers by.
     * @param email the email pattern to filter customers by.
     * @param id the ID to filter customers by.
     * @param isEmailVerified flag to filter customers based on their email verification status.
     * @param username the username pattern to filter customers by.
     * @return a paginated response containing a list of customer DTOs.
     */
    PaginationResponse<List<CustomerDto>> findAllByFilter(Integer page, Integer pageSize, String country, String email, String id, boolean isEmailVerified, String username);

    /**
     * Finds a customer by their unique identifier.
     *
     * @param customerId the unique identifier of the customer to find.
     * @return the customer DTO containing the customer's details.
     */
    CustomerDto findById(UUID customerId);

    /**
     * Updates the details of an existing customer.
     *
     * @param customerId the unique identifier of the customer to update.
     * @param customerDto the DTO containing the updated details of the customer.
     * @param authentication the authentication details of the current user, used to verify permissions.
     */
    void updateCustomerDetails(UUID customerId, CustomerDto customerDto, Authentication authentication);

}
