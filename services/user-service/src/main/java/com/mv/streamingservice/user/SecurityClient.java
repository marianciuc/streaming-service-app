package com.mv.streamingservice.user;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
        name = "security-server",
        url = "${application.config.security-server-url}"
)
public interface SecurityClient {
    @GetMapping("/encode")
    Optional<String> encode(@RequestBody String password);
}
