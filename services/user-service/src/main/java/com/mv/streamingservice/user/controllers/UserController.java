package com.mv.streamingservice.user.controllers;


import com.mv.streamingservice.user.dto.ChangePasswordRequest;
import com.mv.streamingservice.user.dto.CreateEmployeeRequest;
import com.mv.streamingservice.user.enums.APIPath;
import com.mv.streamingservice.user.enums.Role;
import com.mv.streamingservice.user.services.UserService;
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
@RequestMapping(APIPath.USER_V1_CONTROLLER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Changes the password of the authenticated user if the old password matches the current password.
     *
     * @param request the ChangePasswordRequest object containing the old and new passwords
     * @return a ResponseEntity with a status of 200 OK if the password change was successful, or the appropriate error response if it failed
     */
    @PutMapping(APIPath.CHANGE_PASSWORD)
    public ResponseEntity<Void> changePassword(@RequestBody @Validated ChangePasswordRequest request){
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Creates a new employee user with the given request details.
     *
     * @param request the CreateEmployeeRequest object containing the employee details
     * @return the unique identifier (UUID) of the created employee
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(APIPath.CREATE_EMPLOYEE)
    public ResponseEntity<UUID> createEmployee(@RequestBody @Validated CreateEmployeeRequest request) {
        return ResponseEntity.ok(userService.createEmployee(request));
    }


    /**
     * Bans a user in the system by setting the 'isBanned' attribute of the user to true.
     *
     * @param userId the unique identifier of the user to be banned
     * @return the unique identifier of the saved user after banning
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(APIPath.BAN_USER)
    public ResponseEntity<UUID> banUser(@RequestParam("user-id") UUID userId) {
        return ResponseEntity.ok(userService.banUser(userId));
    }

    /**
     * Changes the role of a user in the system.
     *
     * @param role   the new role to be assigned to the user
     * @param userId the unique identifier of the user
     * @return a ResponseEntity with a status of 200 OK if the role change was successful, or an error response if it failed
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SERVICE')")
    @PutMapping(APIPath.CHANGE_ROLE)
    public ResponseEntity<Void> changeRole(
            @RequestParam("role") Role role,
            @RequestParam("user-id") UUID userId
    ){
        userService.updateUserRole(role, userId);
        return ResponseEntity.ok().build();
    }
}
