/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RecordStatus.java
 *
 */

package io.github.marianciuc.streamingservice.media.enums;

/**
 * Defines the status of any given record.
 */
public enum RecordStatus {
    // The record is active and visible
    ACTIVE,

    // The record is deleted and not visible
    DELETED,

    // The record is hidden i.e., it's inactive but not deleted
    HIDDEN
}
