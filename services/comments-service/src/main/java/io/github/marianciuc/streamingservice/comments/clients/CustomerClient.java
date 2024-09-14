/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerClient.java
 *
 */

package io.github.marianciuc.streamingservice.comments.clients;

import io.github.marianciuc.streamingservice.comments.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "customer-service", url = "${application.config.customer-url}")
@Component
public interface CustomerClient {
    @GetMapping("/{customer-id}")
    Optional<CustomerDto> getCustomerById(@PathVariable("customer-id") UUID customerId);
}
