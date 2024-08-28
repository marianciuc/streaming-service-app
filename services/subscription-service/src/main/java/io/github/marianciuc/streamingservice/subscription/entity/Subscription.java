package io.github.marianciuc.streamingservice.subscription.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * The Subscription class represents a subscription that a user can purchase.
 * Each subscription has an id, name, description, created and updated dates,
 * duration in days, price, currency in which the price is set,
 * allowed number of active sessions, record status, resolutions,
 * whether it is temporary, and an id of the next subscription if exists.
 *
 * @author Vladimir Marianciuc
 */
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
@RequiredArgsConstructor
public class Subscription {

    /**
     * Unique identifier for a subscription.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    /**
     * The name of the subscription.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * A detailed description of the subscription.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * The date and time the subscription was created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    /**
     * The date and time the subscription was last modified.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * The duration of the subscription in days.
     */
    @Column(name = "duration_in_days", nullable = false)
    private Integer durationInDays;

    /**
     * The price of the subscription.
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * The currency of the subscription price.
     */
    @Column(name = "currency", nullable = false)
    private Currency currency;

    /**
     * The number of active sessions allowed for this subscription.
     */
    @Column(name = "allowed_active_sessions")
    private Integer allowedActiveSessions;

    /**
     * The record status of the subscription (ACTIVE, DELETED, HIDDEN).
     */
    @Column(name = "record_status")
    private RecordStatus recordStatus;

    /**
     * A set of resolutions associated with the subscription.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "resolution_id"))
    private Set<Resolution> resolutions;

    /**
     * Indicates whether the subscription is temporary.
     */
    @Column(name = "is_temporary")
    private Boolean isTemporary;

    /**
     * The id of the next subscription if subscription is temporary
     */
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "next_subscription_id", referencedColumnName = "id")
    private Subscription nextSubscription;

    private void setNextSubscription(Subscription nextSubscription) {
        this.nextSubscription = nextSubscription;
        this.isTemporary = true;
    }
}
