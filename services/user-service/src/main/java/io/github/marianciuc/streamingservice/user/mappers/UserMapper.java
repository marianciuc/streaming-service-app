package io.github.marianciuc.streamingservice.user.mappers;

import io.github.marianciuc.streamingservice.user.dto.RegistrationRequest;
import io.github.marianciuc.streamingservice.user.dto.UserResponse;
import io.github.marianciuc.streamingservice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromRegistrationRequest(RegistrationRequest request) {
        return User.builder()
                .email(request.email())
                .username(request.username())
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
