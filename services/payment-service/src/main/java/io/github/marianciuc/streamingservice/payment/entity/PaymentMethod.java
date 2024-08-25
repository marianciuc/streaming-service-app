package io.github.marianciuc.streamingservice.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {
    @Id
    private UUID id;
    private UUID customerId;
    private PaymentMethodStatus paymentMethodStatus;
    private String name;
    private String stripePaymentMethodId;
    private String cardLast4;
    private String cardBrand;
    private String cardNumber;
    private String cardExpiryMonth;
    private String cardExpiryYear;
    private String cardCVV;
}
