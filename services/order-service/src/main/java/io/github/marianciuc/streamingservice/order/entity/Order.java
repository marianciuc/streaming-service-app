package io.github.marianciuc.streamingservice.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing an Order in the streaming service.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    /**
     * Unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Unique identifier for the user who placed the order.
     */
    @Column(name = "user_id")
    private UUID userId;

    /**
     * Total amount of the order.
     */
    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * Unique identifier for the payment associated with the order.
     */
    @Column(name = "payment_id")
    private UUID paymentId;

    /**
     * Unique identifier for the subscription associated with the order.
     */
    @Column(name = "subscription_id")
    private UUID subscriptionId;

    /**
     * Date and time when the order was created.
     */
    @CreatedDate
    @Column(name = "order_create_date")
    private LocalDateTime orderCreateDate;

    /**
     * Date and time when the order was completed.
     */
    @Column(name = "order_completed_date")
    private LocalDateTime orderCompletedDate;

    /**
     * Current status of the order.
     *
     * @see OrderStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    /**
     * Scheduled time for when the order should be processed.
     */
    @Column(name = "scheduled_time")
    private LocalDate scheduledTime;
}
