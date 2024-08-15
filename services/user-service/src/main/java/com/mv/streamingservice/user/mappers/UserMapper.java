package com.mv.streamingservice.user.mappers;

import com.mv.streamingservice.user.dto.UserRequest;
import com.mv.streamingservice.user.dto.UserResponse;
import com.mv.streamingservice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.id())
                .role(userRequest.role())
                .userType(userRequest.userType())
                .username(userRequest.username())
                .email(userRequest.email())
                .passwordHash(userRequest.securePassword())
                .build();
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRecordStatus(),
                user.getUserType(),
                user.getRole(),
                user.getIsBanned()
        );
    }
}
