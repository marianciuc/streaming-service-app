package com.mv.streamingservice.order.client;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserClient {

    public UserDetails validateTokenAndGetUserDetails(String token) {
        return null;
    }
}
