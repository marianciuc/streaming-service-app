/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserService.java
 *
 */

package io.github.marianciuc.streamingservice.user.services.impl;


import io.github.marianciuc.streamingservice.user.dto.*;
import io.github.marianciuc.streamingservice.user.entity.User;
import io.github.marianciuc.streamingservice.user.enums.RecordStatus;
import io.github.marianciuc.streamingservice.user.enums.Role;
import io.github.marianciuc.streamingservice.user.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.user.exceptions.SecurityBadCredentialsException;
import io.github.marianciuc.streamingservice.user.mappers.UserMapper;
import io.github.marianciuc.streamingservice.user.repositories.UserRepository;
import io.github.marianciuc.streamingservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The {@code UserServiceImpl} class implements the {@link UserService} interface
 * and provides the functionality to manage user-related operations.
 * @author Vladimir Marianciuc
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String PASSWORD_DOES_NOT_MATCH_MSG = "Password does not match";
    private static final String USER_NOT_FOUND_MSG = "User not found";

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    /**
     *
     * Creates a new User object based on the provided RegistrationRequest and Role.
     *
     * @param request The RegistrationRequest object containing the user's information.
     * @param role The Role object representing the user's role.
     * @return A newly created User object.
     */
    public User createUser(RegistrationRequest request, Role role) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .isBanned(false)
                .recordStatus(RecordStatus.ACTIVE)
                .role(role)
                .build();
    }

    /**
     * Bans a user in the system by setting the 'isBanned' attribute of the user to true.
     *
     * @param userId The unique identifier of the user to be banned.
     */
    public void banUser(UUID userId) {
        User user = this.getUserById(userId);
        user.setIsBanned(true);
        repository.save(user);
    }

    /**
     * Retrieves the UserResponse object for a User with the specified ID.
     *
     * @param userId The ID of the User.
     * @return The UserResponse object containing user details.
     */
    @Override
    public UserResponse getUserResponse(UUID userId) {
        return mapper.toResponse(this.getUserById(userId));
    }


    /**
     * Fetches a User by their ID.
     *
     * @param userId The unique identifier of the User.
     * @return The User that matches the ID.
     * @throws NotFoundException if the User with the specified ID is not found.
     */
    public User getUserById(UUID userId) {
        return repository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MSG));
    }

    /**
     * Updates the role of a user in the system.
     *
     * @param role   the new role to be assigned to the user
     * @param userId the unique identifier of the user
     */
    public void updateUserRole(Role role, UUID userId) {
        User user = this.getUserById(userId);
        user.setRole(role);
        repository.save(user);
    }

    /**
     * Changes the password of the authenticated user if the old password matches the current password.
     *
     * @param request         the ChangePasswordRequest object containing the old and new passwords
     * @param authentication the Authentication object representing the current authentication details
     * @throws SecurityBadCredentialsException if the old password does not match the current password
     */
    public void changePassword(ChangePasswordRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (passwordEncoder.matches(request.oldPassword(), user.getPasswordHash())) {
            String encodedNewPassword = passwordEncoder.encode(request.newPassword());
            user.setPasswordHash(encodedNewPassword);
            repository.save(user);
        }
        else throw new SecurityBadCredentialsException(PASSWORD_DOES_NOT_MATCH_MSG);
    }
}