package io.github.marianciuc.streamingservice.payment.service;

import io.github.marianciuc.streamingservice.payment.dto.InitializePaymentRequest;
import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodRequest;
import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentMethodResponse addPaymentMethod(PaymentMethodRequest paymentMethodRequest);
    PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest paymentMethodRequest);
    void deletePaymentMethod(UUID paymentMethodId);
    void createChargeByOrderId(InitializePaymentRequest request);
}
