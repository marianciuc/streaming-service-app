/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: UserServiceImplTest.java
 *
 */

package io.github.marianciuc.streamingservice.user.services.impl;

import io.github.marianciuc.streamingservice.user.dto.ChangePasswordRequest;
import io.github.marianciuc.streamingservice.user.dto.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.entity.User;
import io.github.marianciuc.streamingservice.user.enums.Role;
import io.github.marianciuc.streamingservice.user.exceptions.SecurityBadCredentialsException;
import io.github.marianciuc.streamingservice.user.mappers.UserMapper;
import io.github.marianciuc.streamingservice.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserMapper userMapper;

    @Test
    void shouldCreateUserWithCorrectValues() {
        RegistrationRequest request = new RegistrationRequest(
                "testUsername",
                "testEmail@example.com",
                "testPassword",
                Role.ROLE_UNSUBSCRIBED_USER
        );

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(request.password())).thenReturn(encodedPassword);

        User expectedUser = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(encodedPassword)
                .isBanned(false)
                .role(request.role())
                .build();

        UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder, userMapper);
        User actualUser = userService.createUser(request, request.role());

        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPasswordHash(), actualUser.getPasswordHash());
        assertEquals(expectedUser.getRole(), actualUser.getRole());
        assertEquals(expectedUser.getIsBanned(), actualUser.getIsBanned());
    }
   @Test
   void shouldChangePasswordWhenOldPasswordMatches() {
       String oldPassword = "oldPassword";
       String newPassword = "newPassword";
       ChangePasswordRequest request = new ChangePasswordRequest(oldPassword, newPassword);
       User user = new User();
       user.setPasswordHash(passwordEncoder.encode(oldPassword));

       Authentication authentication = mock(Authentication.class);
       when(authentication.getPrincipal()).thenReturn(user);
       when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

       UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder, userMapper);
       userService.changePassword(request, authentication);

       verify(passwordEncoder, times(1)).encode(newPassword);
   }

   @Test
   void shouldThrowSecurityBadCredentialsExceptionWhenOldPasswordDoesNotMatch() {
       ChangePasswordRequest request = new ChangePasswordRequest("oldPassword", "newPassword");
       User user = new User();

       Authentication authentication = mock(Authentication.class);
       when(authentication.getPrincipal()).thenReturn(user);
       when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

       UserServiceImpl userService = new UserServiceImpl(userRepository, passwordEncoder, userMapper);

       Exception exception = assertThrows(SecurityBadCredentialsException.class, () -> {
           userService.changePassword(request, authentication);
       });

       String expectedMessage = "Password does not match";
       String actualMessage = exception.getMessage();

      assertTrue(actualMessage.contains(expectedMessage));
   }
}
