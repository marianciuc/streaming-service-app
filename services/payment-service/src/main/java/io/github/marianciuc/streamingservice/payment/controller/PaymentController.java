package io.github.marianciuc.streamingservice.payment.controller;

import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodRequest;
import io.github.marianciuc.streamingservice.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_UNSUBSCRIBED', 'ROLE_SUBSCRUBED')")
    public ResponseEntity<Void> addPaymentMethod(@RequestBody @Valid PaymentMethodRequest paymentMethod) {
        paymentService.addPaymentMethod(paymentMethod);
        return ResponseEntity.ok().build();
    }
}
