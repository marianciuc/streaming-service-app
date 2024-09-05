/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerController.java
 *
 */

package io.github.marianciuc.streamingservice.customer.controllers;

import io.github.marianciuc.streamingservice.customer.dto.CustomerDto;
import io.github.marianciuc.streamingservice.customer.dto.PaginationResponse;
import io.github.marianciuc.streamingservice.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<PaginationResponse<List<CustomerDto>>> getCustomers(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "isEmailVerified", required = false) boolean isEmailVerified
    ) {
        return ResponseEntity.ok(customerService.findAllByFilter(
                page,
                pageSize,
                country,
                email,
                id,
                isEmailVerified,
                username
        ));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customer-id") UUID customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }
}
