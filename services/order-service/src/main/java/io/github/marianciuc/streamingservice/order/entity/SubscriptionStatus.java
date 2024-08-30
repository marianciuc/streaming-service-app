package io.github.marianciuc.streamingservice.order.entity;

import lombok.Getter;

@Getter
public enum SubscriptionStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    PENDING("Pending"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled");

    private final String description;

    SubscriptionStatus(String description) {
        this.description = description;
    }
}