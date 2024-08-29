package io.github.marianciuc.streamingservice.subscription.entity;

import lombok.Getter;

/**
 * The {@code Currency} enum represents different types of currencies.
 * It provides a description for each currency.
 *
 * @see Subscription
 */
@Getter
public enum Currency {
    PLN("Polish Złoty"),
    USD("United States Dollar"),
    EUR("Euro");

    private final String description;

    Currency(String description) {
        this.description = description;
    }

}