package com.mv.streamingservice.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardHolder {
    private UUID userId;
    private String stripeCustomerId;
    private Address address;
    private String cardHolderName;
    private String phoneNumber;
    private List<UUID> paymentMethods;
}
