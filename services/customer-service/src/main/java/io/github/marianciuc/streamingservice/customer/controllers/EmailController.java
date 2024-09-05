/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerController.java
 *
 */

package io.github.marianciuc.streamingservice.customer.controllers;

import io.github.marianciuc.streamingservice.customer.services.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers/email")
@RequiredArgsConstructor
public class EmailController {

    private final CustomerServiceImpl service;

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
