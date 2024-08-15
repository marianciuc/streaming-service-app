package com.mv.streamingservice.user.repositories;

import com.mv.streamingservice.user.entity.RecordStatus;
import com.mv.streamingservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);


    /**
     * Retrieves a user by email or username.
     *
     * @param value the email or username value
     * @return The optional
     */
    @Query("SELECT u FROM User u WHERE u.username = :value OR u.email = :value")
    Optional<User> findByEmailOrUsername(@Param("value") String value);
}
