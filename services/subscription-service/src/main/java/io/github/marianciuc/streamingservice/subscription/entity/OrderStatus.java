package io.github.marianciuc.streamingservice.subscription.entity;

/**
 * Order status enumeration
 */
public enum OrderStatus {
    /**
     * Order was recently created and is ready to be processed, paid, or cancelled.
     */
    CREATED,

    /**
     * Order was successfully paid for and is ready to be processed.
     */
    PAID,

    /**
     * Payment for the order failed due to an error or issue.
     */
    PAYMENT_FAILED,

    /**
     * Order was successfully processed and completed.
     */
    COMPLETED,

    /**
     * Order was cancelled and will not be processed.
     */
    CANCELLED
}
