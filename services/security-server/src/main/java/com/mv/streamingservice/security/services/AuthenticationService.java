package com.mv.streamingservice.security.services;

import com.mv.streamingservice.security.UserMapper;
import com.mv.streamingservice.security.clients.UserClient;
import com.mv.streamingservice.security.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * The {@code AuthenticationService} class is responsible for providing authentication-related functionality.
 * @author Vladimir Marianciuc
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserClient userClient;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Finds a User based on the given query.
     *
     * @param query the query used to find the User by username or email
     * @return an Optional containing the User if found, or an empty Optional if not found
     */
    public Optional<User> findByQuery(String query) {
        return userClient.findByQuery(query).map(userMapper::toUser);
    }

    /**
     * Encodes the provided password using BCryptPasswordEncoder.
     *
     * @param password the password to be encoded
     * @return the encoded password
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
