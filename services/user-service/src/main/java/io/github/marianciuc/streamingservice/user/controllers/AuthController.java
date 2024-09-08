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
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok().build();
    }
}
