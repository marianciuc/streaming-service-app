package com.mv.streamingservice.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {
    @Id
    private String id;
    private String stripeChargeId;
    private String invoiceId;
    private Long amountPaid;
    private PaymentStatus status;
    private String failureMessage;
    private String createdAt;
}
