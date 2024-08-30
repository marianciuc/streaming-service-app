package io.github.marianciuc.streamingservice.user.controllers;

import io.github.marianciuc.streamingservice.user.dto.CredentialsRequest;
import io.github.marianciuc.streamingservice.user.dto.JsonWebTokenResponse;
import io.github.marianciuc.streamingservice.user.dto.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.enums.APIPath;
import io.github.marianciuc.streamingservice.user.services.AuthService;
import io.github.marianciuc.streamingservice.user.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code AuthController} class handles API endpoints related to user authentication and authorization.
 * @see UserServiceImpl
 */
@RestController
@RequestMapping(APIPath.AUTH_V1_CONTROLLER)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user with the provided registration request details.
     *
     * @param request the registration request object containing the user details
     * @return the user response object with the details of the registered user
     */
    @PostMapping(APIPath.REGISTER)
    public ResponseEntity<JsonWebTokenResponse> register(@RequestBody RegistrationRequest request, Authentication authentication) {
        return ResponseEntity.ok(authService.registerUser(request, authentication));
    }

    /**
     * Authenticates a user with the provided credentials and returns a JSON Web Token response.
     *
     * @param request the CredentialsRequest object containing the user's login and password
     * @return a ResponseEntity containing an object of type JsonWebTokenResponse with the access and refresh tokens
     */
    @PostMapping(APIPath.LOGIN)
    public ResponseEntity<JsonWebTokenResponse> login(@RequestBody CredentialsRequest request) {
        return ResponseEntity.ok(authService.authenticateUserByCredentials(request));
    }

    /**
     * Refreshes the access and refresh tokens for the specified user.
     *
     * @param refreshToken the refresh token used to generate new tokens
     * @return an object of type JsonWebTokenResponse containing the new access and refresh tokens
     */
    @PostMapping(APIPath.REFRESH)
    public ResponseEntity<JsonWebTokenResponse> refreshToken(@RequestParam("token") String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
