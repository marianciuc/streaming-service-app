package io.github.marianciuc.streamingservice.payment.entity;

import io.github.marianciuc.streamingservice.payment.enums.Currency;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "cardholder_id", referencedColumnName = "id")
    private CardHolder cardHolder;

    @Column(name = "stripe_payment_intent_id")
    private String stripePaymentIntentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    private Long amount;

    @Column(name = "failure_message")
    private String failureMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
