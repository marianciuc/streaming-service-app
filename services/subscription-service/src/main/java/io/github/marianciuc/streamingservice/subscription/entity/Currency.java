package io.github.marianciuc.streamingservice.subscription.entity;

import lombok.Getter;

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