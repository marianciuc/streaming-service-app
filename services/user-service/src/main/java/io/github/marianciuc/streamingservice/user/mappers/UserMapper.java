package io.github.marianciuc.streamingservice.user.mappers;

import io.github.marianciuc.streamingservice.user.dto.responses.UserResponse;
import io.github.marianciuc.streamingservice.user.entity.User;
import io.github.marianciuc.streamingservice.user.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRecordStatus(),
                Role.valueOf(user.getRole()),
                user.getIsBanned()
        );
    }
}
