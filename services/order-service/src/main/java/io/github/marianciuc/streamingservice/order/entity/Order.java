package com.mv.streamingservice.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    private UUID id;
    private UUID customerId;
    private BigDecimal amount;
    private UUID subscriptionId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
}
