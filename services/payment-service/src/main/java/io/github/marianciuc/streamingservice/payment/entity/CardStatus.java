package io.github.marianciuc.streamingservice.payment.entity;


import lombok.Getter;

@Getter
public enum CardStatus {

    ACTIVE("active"),
    INACTIVE("inactive");

    private final String status;

    CardStatus(String status) {
        this.status = status;
    }
}
