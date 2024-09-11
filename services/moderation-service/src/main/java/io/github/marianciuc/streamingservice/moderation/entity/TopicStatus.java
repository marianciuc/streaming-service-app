/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: TopicStatus.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.entity;

public enum TopicStatus {
    /**
     * Indicates that the topic is currently open.
     */
    OPEN("Indicates that the topic is currently open"),

    /**
     * Indicates that work is currently being done on the topic.
     */
    IN_PROGRESS("Indicates that work is currently being done on the topic"),

    /**
     * Indicates that the topic has been closed.
     */
    CLOSED("Indicates that the topic has been closed"),

    /**
     * Indicates that work on the topic is currently on hold.
     */
    ON_HOLD("Indicates that work on the topic is currently on hold");

    private final String description;

    TopicStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}