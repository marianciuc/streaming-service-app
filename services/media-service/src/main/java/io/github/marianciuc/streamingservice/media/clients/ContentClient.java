/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ContentClient.java
 *
 */

package io.github.marianciuc.streamingservice.media.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient
@Component
public interface ContentClient {

    @PostMapping("/episodes/{content-id}/{media-id}")
    ResponseEntity<Void> addEpisodeMediaId(@PathVariable("content-id") UUID contentId, @PathVariable("media-id") UUID mediaId);

    @PostMapping("/movies/{content-id}/{media-id}")
    ResponseEntity<Void> addMovieMediaId(@PathVariable("content-id") UUID contentId, @PathVariable("media-id") UUID mediaId);
}
