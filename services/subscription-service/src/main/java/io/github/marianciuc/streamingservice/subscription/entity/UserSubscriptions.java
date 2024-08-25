package io.github.marianciuc.streamingservice.subscription.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptions {
    @Id
    private UUID id;
    private UUID userId;
    private UUID orderId;
    private UUID subscriptionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;
}
