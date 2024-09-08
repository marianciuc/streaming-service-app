package io.github.marianciuc.streamingservice.user.controllers;

import io.github.marianciuc.streamingservice.user.dto.requests.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.enums.APIPath;
import io.github.marianciuc.streamingservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code AuthController} class handles API endpoints related to user authentication and authorization.
 */
@RestController
@RequestMapping(APIPath.AUTH_V1_CONTROLLER)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(APIPath.REGISTER)
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request, Authentication authentication) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }
}
