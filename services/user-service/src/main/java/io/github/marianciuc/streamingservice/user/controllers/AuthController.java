package io.github.marianciuc.streamingservice.user.controllers;

import io.github.marianciuc.streamingservice.user.dto.requests.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code AuthController} class handles API endpoints related to user authentication and authorization.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }
}
