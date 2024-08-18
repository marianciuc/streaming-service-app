package com.mv.streamingservice.user.enums;

import lombok.Getter;

/**
 * The {@code UserType} enum represents the different types of users in the system.
 */
@Getter
public enum UserType {

    /**
     * The {@code CUSTOMER} variable represents the user type for a customer in the system.
     * It is part of the {@link UserType} enum and has a display name of "Customer".
     *
     * @see UserType
     */
    CUSTOMER("Customer"),

    /**
     * The {@code EMPLOYEE} variable represents the user type for an employee in the system.
     * It is part of the {@link UserType} enum and has a display name of "Employee".
     *
     * @see UserType
     */
    EMPLOYEE("Employee");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

}