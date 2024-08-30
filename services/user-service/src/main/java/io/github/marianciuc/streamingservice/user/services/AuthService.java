package io.github.marianciuc.streamingservice.user.services;

import io.github.marianciuc.streamingservice.user.dto.CredentialsRequest;
import io.github.marianciuc.streamingservice.user.dto.JsonWebTokenResponse;
import io.github.marianciuc.streamingservice.user.dto.RegistrationRequest;
import org.springframework.security.core.Authentication;

/**
 * The AuthService interface defines the methods to handle user authentication functionalities.
 * @author Vladimir Marianciuc
 */
public interface AuthService {

    /**
     * Performs user authentication based on provided credentials.
     *
     * @param request An instance of {@code CredentialsRequest} containing the user's credentials.
     * @return An instance of {@code JsonWebTokenResponse} containing the JWT token details for the authenticated user.
     */
    JsonWebTokenResponse authenticateUserByCredentials(CredentialsRequest request);

    /**
     * Refreshes the JWT token for a user.
     *
     * @param refreshToken The refresh token string.
     * @return An instance of {@code JsonWebTokenResponse} containing the new JWT token details.
     */
    JsonWebTokenResponse refreshToken(String refreshToken);

    /**
     * Registers a new user in the system and performs initial authentication.
     *
     * @param request An instance of {@code RegistrationRequest} containing the new user's details.
     * @param authentication The {@code Authentication} object that represents the principal in an authentication request.
     * @return An instance of {@code JsonWebTokenResponse} containing the JWT token details for the newly registered user.
     */
    JsonWebTokenResponse registerUser(RegistrationRequest request, Authentication authentication);
}
