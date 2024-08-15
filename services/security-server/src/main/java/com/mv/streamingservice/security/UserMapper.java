package com.mv.streamingservice.security;

import com.mv.streamingservice.security.dto.UserResponse;
import com.mv.streamingservice.security.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User toUser(UserResponse response) {
        return User.builder()
                .username(response.username())
                .password(response.passwordHash())
                .recordStatus(response.recordStatus())
                .isAccountNonLocked(!response.isBanned())
                .authorities(List.of(new SimpleGrantedAuthority(response.role().name())))
                .userType(response.userType())
                .build();
    }
}
