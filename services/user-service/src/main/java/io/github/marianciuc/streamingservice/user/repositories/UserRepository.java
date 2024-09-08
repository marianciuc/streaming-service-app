package io.github.marianciuc.streamingservice.user.repositories;

import io.github.marianciuc.streamingservice.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository interface to manage data related to the user entity.
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
     * Finds a User by their email or username.
     * @param value The email or username of the User.
     * @return User if email or username exists, else Optional.empty().
     */
    @Query("SELECT u FROM User u WHERE u.username = :value OR u.email = :value")
    @Transactional(readOnly = true)
    Optional<User> findByEmailOrUsername(@Param("value") String value);

    boolean existsByEmailOrUsername(@Email(message = "Email should be valid") String email, @NotBlank(message = "Username is mandatory") String username);
}
