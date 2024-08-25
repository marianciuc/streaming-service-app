package io.github.marianciuc.streamingservice.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {
    @Id
    private UUID id;
    private UUID paymentMethodId;
    private String stripePaymentId;
    private Long amountInCents;
    private Currency currency;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
