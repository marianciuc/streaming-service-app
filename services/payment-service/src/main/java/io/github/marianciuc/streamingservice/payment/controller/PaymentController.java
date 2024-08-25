package io.github.marianciuc.streamingservice.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @PostMapping
    public ResponseEntity<Void> processPayment() {
        return null;
    }
}
