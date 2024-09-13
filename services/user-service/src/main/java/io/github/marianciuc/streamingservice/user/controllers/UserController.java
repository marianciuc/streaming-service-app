package io.github.marianciuc.streamingservice.user.controllers;

import io.github.marianciuc.streamingservice.user.dto.requests.ChangePasswordRequest;
import io.github.marianciuc.streamingservice.user.enums.Role;
import io.github.marianciuc.streamingservice.user.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;



/**
 * The UserController class handles requests related to user operations.
 * It defines various methods for changing passwords, creating employees, banning users, and changing user roles.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    /**
     * Changes the password of the authenticated user if the old password matches the current password.
     *
     * @param request the ChangePasswordRequest object containing the old and new passwords
     * @return a ResponseEntity with a status of 200 OK if the password change was successful, or the appropriate error response if it failed
     */
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody @Validated ChangePasswordRequest request){
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Bans a user in the system by setting the 'isBanned' attribute of the user to true.
     *
     * @param userId the unique identifier of the user to be banned
     * @return the unique identifier of the saved user after banning
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/ban/{user-id}")
    public ResponseEntity<Void> banUser(@PathVariable("user-id") UUID userId) {
        userService.banUser(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Changes the role of a user in the system.
     *
     * @param role   the new role to be assigned to the user
     * @param userId the unique identifier of the user
     * @return a ResponseEntity with a status of 200 OK if the role change was successful, or an error response if it failed
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/role")
    public ResponseEntity<Void> changeRole(
            @RequestParam("role") Role role,
            @RequestParam("user-id") UUID userId
    ){
        userService.updateUserRole(role, userId);
        return ResponseEntity.ok().build();
    }
}
