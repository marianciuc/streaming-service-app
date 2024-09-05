/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerController.java
 *
 */

package io.github.marianciuc.streamingservice.customer.controllers;

import io.github.marianciuc.streamingservice.customer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers/email")
@RequiredArgsConstructor
public class EmailController {

    private final CustomerService service;

    @PostMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam("verifyCode") String verifyCode, Authentication authentication) {
        service.verifyCode(verifyCode, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/start-email-verification")
    public ResponseEntity<Void> startEmailVerification(Authentication authentication) {
        service.startEmailVerification(authentication);
        return ResponseEntity.ok().build();
    }
}
