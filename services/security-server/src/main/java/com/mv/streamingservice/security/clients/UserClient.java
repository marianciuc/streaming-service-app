package com.mv.streamingservice.security.clients;

import com.mv.streamingservice.security.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "user-service",
        url = "${application.config.users-service-url}"
)
public interface UserClient {
    @GetMapping("/{query}")
    Optional<UserResponse> findByQuery(@PathVariable("query") String query);
}
