package io.github.marianciuc.streamingservice.subscription.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * This class represents a user's subscription.
 * It models a single user subscription along with the necessary details,
 * such as user id, order id, subscription details and period of subscription.
 *
 * @author Vladimir Marianciuc
 */
@Data
@Entity
@Table(name = "user_subscriptions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptions {

    /**
     * Unique identifier for the user subscription.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * User id associated with the subscription.
     */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * Order id associated with the subscription.
     */
    @Column(name = "order_id", nullable = false, unique = true)
    private UUID orderId;

    /**
     * Subscription details.
     */
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    /**
     * The start date of the subscription.
     */
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * The end date of the subscription.
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * The status of the subscription.
     */
    @Column(name = "status")
    private SubscriptionStatus status;
}
