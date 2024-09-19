/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserService.java
 *
 */

package io.github.marianciuc.streamingservice.user.services;

import io.github.marianciuc.streamingservice.user.dto.requests.ChangePasswordRequest;
import io.github.marianciuc.streamingservice.user.dto.requests.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.dto.responses.UserResponse;
import io.github.marianciuc.streamingservice.user.entity.User;
import io.github.marianciuc.streamingservice.user.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    /**
     * Creates a User based on the RegistrationRequest and Role.
     * @param request A representation of a RegistrationRequest.
     * @return The User that was created.
     */
    void createUser(RegistrationRequest request);

    /**
     * Changes the existing User's password
     * @param request A representation of ChangePasswordRequest.
     */
    void changePassword(ChangePasswordRequest request);

    /**
     * Updates the Role of an existing User.
     * @param role A representation of a Role.
     * @param userId An ID of a User.
     */
    void updateUserRole(Role role, UUID userId);

    /**
     * Fetch a User by their ID.
     * @param userId An ID of a User.
     * @return The User that matches the ID.
     */
    User getUserById(UUID userId);

    /**
     * Bans a User by their ID. If user is already banned, this method will unban them.
     * @param userId An ID of a User.
     */
    void banUser(UUID userId);

    /**
     * Retrieves the UserResponse object for a User with the specified ID.
     *
     * @param userId The ID of the User.
     * @return The UserResponse object containing user details.
     * @see UserResponse
     */
    UserResponse getUserResponse(UUID userId);
}
