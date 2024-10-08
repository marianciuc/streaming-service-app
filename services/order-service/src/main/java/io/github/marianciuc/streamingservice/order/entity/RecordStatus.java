package io.github.marianciuc.streamingservice.order.entity;

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
