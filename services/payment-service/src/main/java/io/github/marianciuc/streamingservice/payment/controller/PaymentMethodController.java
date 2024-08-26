package io.github.marianciuc.streamingservice.payment.controller;


import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodRequest;
import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodResponse;
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
    public ResponseEntity<PaymentMethodResponse> addPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        return ResponseEntity.ok(paymentService.addPaymentMethod(paymentMethodRequest));
    }

    @PutMapping
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        return null;
    }

    @GetMapping
    public ResponseEntity<PaymentMethodResponse> getPaymentMethod(@RequestParam("id") UUID id) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePaymentMethod(@RequestParam("id") UUID id) {
        return null;
    }
}
