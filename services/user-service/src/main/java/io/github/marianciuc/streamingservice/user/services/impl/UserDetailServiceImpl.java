/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserDetailServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.user.services.impl;

import io.github.marianciuc.streamingservice.user.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * The UserDetailServiceImpl class implements the UserDetailsService interface
 * to provide user details for authentication and authorization.
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User not found";
    private final UserRepository userRepository;


    /**
     * Loads the user details for authentication and authorization based on the provided username.
     *
     * @param username the username of the user
     * @return the UserDetails object containing the user details
     * @throws NotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmailOrUsername(username).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MSG));
    }
}
