package io.github.marianciuc.streamingservice.payment.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Refund {
    @Id
    private UUID id;
    private Long amountInCents;
    private String stripeRefundId;
    private RefundStatus status;
    private LocalDateTime createdAt;
}
