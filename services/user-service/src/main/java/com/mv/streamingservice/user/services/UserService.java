package com.mv.streamingservice.user.services;

import com.mv.streamingservice.user.dto.UserRequest;
import com.mv.streamingservice.user.dto.UserResponse;
import com.mv.streamingservice.user.entity.RecordStatus;
import com.mv.streamingservice.user.entity.User;
import com.mv.streamingservice.user.exceptions.NotFoundException;
import com.mv.streamingservice.user.exceptions.UserIsBanned;
import com.mv.streamingservice.user.mappers.UserMapper;
import com.mv.streamingservice.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * Creates a new user based on the provided user request.
     *
     * @param request the user request containing the user details
     * @return {@code UUID}
     */
    public UUID createUser(UserRequest request) {
        User user = userMapper.toEntity(request);
        user.setPasswordHash(encodePassword(request.securePassword()));
        user.setRecordStatus(RecordStatus.ACTIVE);
        user.setIsBanned(false);
        return userRepository.save(user).getId();
    }

    /**
     * Bans a user by setting their 'isBanned' flag to true.
     *
     * @param userId the UUID of the user to ban
     * @return the UUID of the banned user
     */
    public UUID banUser(UUID userId) {
        User user = this.getById(userId);
        user.setIsBanned(true);
        return userRepository.save(user).getId();
    }

    /**
     * Finds a user by username or email.
     *
     * @param query the username or email query to search for
     * @return the UserResponse object corresponding to the query
     * @throws NotFoundException if the user with the given username or email is not found
     */
    public UserResponse findByUsernameOrEmail(String query) {
        User user = userRepository.findByEmailOrUsername(query).orElseThrow(() -> new NotFoundException("User not found"));
        userIsBanned(user);
        return userMapper.toResponse(user);
    }

    public UUID updateUser(UUID userId, UserRequest request) {
        User existingUser = this.getById(userId);

        return null;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId the UUID of the user to retrieve.
     * @return the User object corresponding to the given ID.
     * @throws NotFoundException if the user with the given ID is not found.
     */
    public User getById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Checks if a user is banned.
     *
     * @param user the user to check
     * @throws UserIsBanned if the user is already banned
     */
    public void userIsBanned(User user) {
        if (user.getIsBanned()) throw new UserIsBanned("User is already banned");
    }
}
