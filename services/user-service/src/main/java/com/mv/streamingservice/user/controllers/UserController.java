package com.mv.streamingservice.user.controllers;

import com.mv.streamingservice.user.dto.UserRequest;
import com.mv.streamingservice.user.dto.UserResponse;
import com.mv.streamingservice.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody UserRequest request) {
        log.info("Creating user: {}", request);
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{query}")
    public ResponseEntity<UserResponse> getUserByUsernameOrEmail(@PathVariable String query) {
        return ResponseEntity.ok(userService.findByUsernameOrEmail(query));
    }
}
