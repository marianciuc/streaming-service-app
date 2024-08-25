package com.mv.streamingservice.user.services;

import com.mv.streamingservice.user.controllers.UserDetailsRequest;
import com.mv.streamingservice.user.dto.CreateEmployeeRequest;
import com.mv.streamingservice.user.dto.*;
import com.mv.streamingservice.user.enums.RecordStatus;
import com.mv.streamingservice.user.enums.Role;
import com.mv.streamingservice.user.entity.User;
import com.mv.streamingservice.user.enums.UserType;
import com.mv.streamingservice.user.exceptions.NotFoundException;
import com.mv.streamingservice.user.exceptions.SecurityBadCredentialsException;
import com.mv.streamingservice.user.mappers.UserMapper;
import com.mv.streamingservice.user.repositories.UserRepository;
import io.github.marianciuc.jwtsecurity.service.JsonWebTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The {@code UserService} class provides functionality related to user management.
 * It handles operations such as creating a new user, banning a user, updating a user, and finding users by username or email.
 *
 * @author Vladimir Marianciuc
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JsonWebTokenService jsonWebTokenService;
    private final UserDetailServiceImpl userDetailsService;


    /**
     * Creates a user object with the provided username, email, password, role, and user type.
     *
     * @param username the username of the user
     * @param email    the email of the user
     * @param password the password of the user
     * @param role     the role of the user
     * @param userType the user type of the user
     * @return the created user object
     */
    private User createUser(String username, String email, String password, Role role, UserType userType) {
        return User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .isBanned(false)
                .recordStatus(RecordStatus.ACTIVE)
                .role(role)
                .userType(userType)
                .build();
    }

    /**
     * Creates a User object from the provided RegistrationRequest, Role, and UserType.
     *
     * @param request  the RegistrationRequest object containing the user details
     * @param role     the Role of the user
     * @param userType the UserType of the user
     * @return the created User object
     */
    private User createUserFromRequest(RegistrationRequest request, Role role, UserType userType) {
        return createUser(request.username(), request.email(), request.password(), role, userType);
    }

    /**
     * Registers a new user with the provided registration request details.
     *
     * @param request the registration request object containing the user details
     * @return the user response object with the details of the registered user
     */
    public UserResponse registerUser(RegistrationRequest request) {
        User user = createUserFromRequest(request, Role.ROLE_UNSUBSCRIBED_USER, UserType.CUSTOMER);
        return userMapper.toResponse(saveUserDetails(user));
    }

    /**
     * Creates a new employee user with the given request details.
     *
     * @param request the CreateEmployeeRequest object containing the employee details
     * @return the unique identifier (UUID) of the created employee
     */
    public UUID createEmployee(CreateEmployeeRequest request) {
        User user = createUser(request.username(), request.email(), request.password(), request.role(), UserType.EMPLOYEE);
        return saveUserDetails(user).getId();
    }

    /**
     * Authenticates a user with the provided credentials and returns a JSON Web Token response.
     *
     * @param request the CredentialsRequest object containing the user's login and password
     * @return an object of type JsonWebTokenResponse containing the access and refresh tokens
     * @throws Exception if authentication fails or credentials are incorrect
     */
    public JsonWebTokenResponse authenticateUserByCredentials(CredentialsRequest request) {
        try {
            authenticateUser(request.login(), request.password());
        } catch (BadCredentialsException e) {
            throw new SecurityBadCredentialsException("Incorrect username or password", e);
        }
        User user = getAuthenticatedUser();
        return new JsonWebTokenResponse(
                jsonWebTokenService.generateAccessToken(user),
                jsonWebTokenService.generateRefreshToken(user)
        );
    }

    /**
     * Authenticates a user with the provided login and password.
     *
     * @param login    the login of the user to be authenticated
     * @param password the password of the user to be authenticated
     */
    private void authenticateUser(String login, String password) {
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(login, password);
        authenticationManager.authenticate(userToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The authenticated user or throws an AuthenticationServiceException if authentication fails.
     */
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new AuthenticationServiceException("Authentication failed");
    }

    /**
     * Bans a user in the system by setting the 'isBanned' attribute of the user to true.
     *
     * @param userId the unique identifier of the user to be banned
     * @return the unique identifier of the saved user after banning
     */
    public UUID banUser(UUID userId) {
        User user = this.getById(userId);
        user.setIsBanned(true);
        return saveUserDetails(user).getId();
    }

    /**
     * Retrieves a User object by its unique identifier.
     *
     * @param userId the unique identifier of the user
     * @return the User object with the specified identifier
     * @throws NotFoundException if the user with the specified identifier is not found
     */
    public User getById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Refreshes the access and refresh tokens for the specified user.
     *
     * @param refreshToken the refresh token used to generate new tokens
     * @return an object of type JsonWebTokenResponse containing the new access and refresh tokens
     */
    public JsonWebTokenResponse refreshToken(String refreshToken) {
        UserDetails userDetails = jsonWebTokenService.parseRefreshToken(refreshToken);
        User user = userRepository.findById(UUID.fromString(userDetails.getUsername())).orElseThrow(() -> new NotFoundException("User not found"));

        return new JsonWebTokenResponse(
                jsonWebTokenService.generateAccessToken(user),
                jsonWebTokenService.generateRefreshToken(user)
        );
    }

    /**
     * Updates the role of a user.
     *
     * @param role   the new role to be assigned to the user
     * @param userId the unique identifier of the user
     * @throws NotFoundException if the user with the specified user ID is not found
     */
    public void updateUserRole(Role role, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setRole(role);
        saveUserDetails(user);
    }

    /**
     * Changes the password of the authenticated user if the old password matches the current password.
     *
     * @param request the ChangePasswordRequest object containing the old and new passwords
     */
    public void changePassword(ChangePasswordRequest request) {
        User user = getAuthenticatedUser();
        String encodedOldPassword = passwordEncoder.encode(request.oldPassword());
        if (encodedOldPassword.equals(user.getPasswordHash())) {
            String encodedNewPassword = passwordEncoder.encode(request.newPassword());
            user.setPasswordHash(encodedNewPassword);
            saveUserDetails(user);
        }
    }

    /**
     * Saves the user details in the database.
     *
     * @param user the user object containing the details to be saved
     * @return the saved user object
     */
    private User saveUserDetails(User user) {
        return userRepository.save(user);
    }
}
