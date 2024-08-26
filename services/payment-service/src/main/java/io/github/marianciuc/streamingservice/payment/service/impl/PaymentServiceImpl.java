package io.github.marianciuc.streamingservice.payment.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import io.github.marianciuc.streamingservice.payment.dto.InitializePaymentRequest;
import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodRequest;
import io.github.marianciuc.streamingservice.payment.dto.PaymentMethodResponse;
import io.github.marianciuc.streamingservice.payment.entity.PaymentMethod;
import io.github.marianciuc.streamingservice.payment.entity.PaymentMethodStatus;
import io.github.marianciuc.streamingservice.payment.exceptions.PaymentException;
import io.github.marianciuc.streamingservice.payment.mappers.PaymentMethodMapper;
import io.github.marianciuc.streamingservice.payment.repository.PaymentMethodRepository;
import io.github.marianciuc.streamingservice.payment.service.PaymentService;
import io.github.marianciuc.streamingservice.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String apiKey;

    private final PaymentMethodMapper paymentMethodMapper;
    private final PaymentMethodRepository paymentMethodRepository;
    private final UserService userService;

    public PaymentMethodResponse addPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod localPaymentMethod = paymentMethodMapper.fromRequest(paymentMethodRequest);
        UserDetails userDetails = userService.getUser();
        localPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.PENDING);
        localPaymentMethod.setCustomerId(UUID.fromString(userDetails.getUsername()));

        try {
            PaymentMethodCreateParams params = PaymentMethodCreateParams.builder()
                    .setType(PaymentMethodCreateParams.Type.CARD)
                    .setCard(PaymentMethodCreateParams.CardDetails.builder()
                            .setNumber(localPaymentMethod.getCardNumber())
                            .setExpMonth(localPaymentMethod.getCardExpiryMonth())
                            .setExpYear(localPaymentMethod.getCardExpiryYear())
                            .setCvc(localPaymentMethod.getCardCVV())
                            .build()
                    )
                    .build();
            com.stripe.model.PaymentMethod stripePaymentMethod = com.stripe.model.PaymentMethod.create(params);
            localPaymentMethod.setStripePaymentMethodId(stripePaymentMethod.getId());
            localPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.CONFIRMED);
        } catch (StripeException e) {
            localPaymentMethod.setPaymentMethodStatus(PaymentMethodStatus.CONFIRMATION_ERROR);
            throw new PaymentException("Error while payment process");
        }
        PaymentMethod newPaymentMethod = paymentMethodRepository.save(localPaymentMethod);
        return paymentMethodMapper.toResponse(newPaymentMethod);
    }

    @Override
    public PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        return null;
    }

    @Override
    public void deletePaymentMethod(UUID paymentMethodId) {

    }

    @Override
    public void createChargeByOrderId(InitializePaymentRequest request) {

        ChargeCreateParams params = ChargeCreateParams.builder()
                        .setAmount(1099L)
                        .setCurrency("usd")
                        .setSource("tok_visa")
                        .build();
        try {
            Charge charge = Charge.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }


}
