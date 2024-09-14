/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ContentClient.java
 *
 */

package io.github.marianciuc.streamingservice.comments.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "content-service", url = "${application.config.content-url}")
@Component
public interface ContentClient {

    @GetMapping("/{id}")
    Boolean existsById(@PathVariable UUID id);
}
