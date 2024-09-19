/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserService.java
 *
 */

package io.github.marianciuc.streamingservice.user.services.impl;


import io.github.marianciuc.streamingservice.user.kafka.messages.CreateUserMessage;
import io.github.marianciuc.streamingservice.user.dto.requests.ChangePasswordRequest;
import io.github.marianciuc.streamingservice.user.dto.requests.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.dto.responses.UserResponse;
import io.github.marianciuc.streamingservice.user.entity.User;
import io.github.marianciuc.streamingservice.user.enums.RecordStatus;
import io.github.marianciuc.streamingservice.user.enums.Role;
import io.github.marianciuc.streamingservice.user.exceptions.IllegalArgumentException;
import io.github.marianciuc.streamingservice.user.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.user.exceptions.SecurityBadCredentialsException;
import io.github.marianciuc.streamingservice.user.kafka.KafkaUserProducer;
import io.github.marianciuc.streamingservice.user.repositories.UserRepository;
import io.github.marianciuc.streamingservice.user.security.utils.SecurityUtils;
import io.github.marianciuc.streamingservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final KafkaUserProducer kafkaUserProducer;

    public void createUser(RegistrationRequest request) {
        if (repository.existsByEmailOrUsername(request.email(), request.username())) {
            throw new IllegalArgumentException("User with this email or username already exists");
        }
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .isBanned(false)
                .recordStatus(RecordStatus.ACTIVE)
                .role(request.role())
                .build();
        User newUser = repository.save(user);
        kafkaUserProducer.sendUserCreatedMessage(new CreateUserMessage(newUser.getId(), newUser.getEmail(), newUser.getUsername()));
    }

    public void banUser(UUID userId) {
        User user = this.getUserById(userId);
        user.setIsBanned(!user.getIsBanned());
        repository.save(user);
    }

    @Override
    public UserResponse getUserResponse(UUID userId) {
        return UserResponse.toResponse(this.getUserById(userId));
    }


    public User getUserById(UUID userId) {
        return repository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MSG));
    }

    public void updateUserRole(Role role, UUID userId) {
        User user = this.getUserById(userId);
        user.setRole(role);
        repository.save(user);
    }

    public void changePassword(ChangePasswordRequest request) {
        User user = this.getUserById(SecurityUtils.extractJwtUserPrincipals().getId());
        if (passwordEncoder.matches(request.oldPassword(), user.getPasswordHash())) {
            String encodedNewPassword = passwordEncoder.encode(request.newPassword());
            user.setPasswordHash(encodedNewPassword);
            repository.save(user);
        }
        else throw new SecurityBadCredentialsException(PASSWORD_DOES_NOT_MATCH_MSG);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmailOrUsername(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }
}
