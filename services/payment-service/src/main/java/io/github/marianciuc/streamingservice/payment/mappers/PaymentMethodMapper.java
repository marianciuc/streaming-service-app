package io.github.marianciuc.streamingservice.payment.mappers;

import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodRequest;
import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodResponse;
import io.github.marianciuc.streamingservice.payment.entity.PaymentMethod;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentMethodMapper {
    public PaymentMethod fromRequest(PaymentMethodRequest paymentMethodRequest) {
        return PaymentMethod.builder()
                .cardBrand(paymentMethodRequest.cardBrand())
                .cardNumber(paymentMethodRequest.cardNumber())
                .cardExpiryMonth(Long.parseLong(paymentMethodRequest.cardExpiryMonth()))
                .cardExpiryYear(Long.parseLong(paymentMethodRequest.cardExpiryYear()))
                .cardLast4(paymentMethodRequest.cardNumber().substring(paymentMethodRequest.cardNumber().length() - 4))
                .cardCVV(paymentMethodRequest.cardCVV())
                .name(paymentMethodRequest.name())
                .build();
    }

    public PaymentMethodResponse toResponse(PaymentMethod entity) {
        return new PaymentMethodResponse(
                entity.getName(),
                entity.getCardBrand(),
                entity.getId(),
                entity.getCardLast4(),
                entity.getPaymentMethodStatus()
        );
    }
}
