package com.mv.streamingservice.subscription.controller;

import com.mv.streamingservice.subscription.dto.SubscriptionRequest;
import com.mv.streamingservice.subscription.dto.SubscriptionResponse;
import com.mv.streamingservice.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions() {
        return null;
    }

    @GetMapping
    public ResponseEntity<SubscriptionResponse> getSubscription(@RequestParam String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<UUID> addSubscription(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.createSubscription(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateSubscription(@RequestParam("id") UUID id, @RequestBody SubscriptionRequest request) {
        subscriptionService.updateSubscription(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSubscription(@RequestParam("id") UUID id) {
        subscriptionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
