package io.github.marianciuc.streamingservice.payment.controller;


import io.github.marianciuc.streamingservice.payment.dto.PaymentRequest;
import io.github.marianciuc.streamingservice.payment.dto.PaymentResponse;
import io.github.marianciuc.streamingservice.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments/payment-methods")
public class PaymentMethodController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> addPaymentMethod(PaymentRequest paymentRequest) {
        return null;
    }

    @PutMapping
    public ResponseEntity<PaymentResponse> updatePaymentMethod(PaymentRequest paymentRequest) {
        return null;
    }

    @GetMapping
    public ResponseEntity<PaymentResponse> getPaymentMethod(@RequestParam("id") UUID id) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePaymentMethod(@RequestParam("id") UUID id) {
        return null;
    }
}
