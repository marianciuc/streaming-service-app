package io.github.marianciuc.streamingservice.subscription.controller;

import io.github.marianciuc.streamingservice.subscription.dto.SubscriptionRequest;
import io.github.marianciuc.streamingservice.subscription.dto.SubscriptionResponse;
import io.github.marianciuc.streamingservice.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class provides endpoints for managing Subscription objects.
 * It includes endpoints for retrieving all subscriptions, retrieving a single
 * subscription, adding a subscription, updating a subscription, and deleting a subscription.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * This endpoint retrieves all Subscription objects.
     *
     * @return ResponseEntity containing a list of all SubscriptionResponse objects.
     */
    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    /**
     * This endpoint retrieves a single Subscription object.
     *
     * @param id The UUID of the Subscription to retrieve.
     * @return ResponseEntity containing the SubscriptionResponse object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionResponse(id));
    }

    /**
     * This endpoint adds a new Subscription object.
     *
     * @param request The details of the Subscription to add.
     * @return ResponseEntity containing the added SubscriptionResponse object.
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<SubscriptionResponse> addSubscription(@RequestBody @Valid SubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.createSubscription(request));
    }


    /**
     * This endpoint updates an existing Subscription object.
     *
     * @param id The UUID of the Subscription to update.
     * @param request The new details of the Subscription to update.
     * @return ResponseEntity containing the updated SubscriptionResponse object.
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> updateSubscription(@RequestParam("id") UUID id, @RequestBody @Valid SubscriptionRequest request) {
        subscriptionService.updateSubscription(id, request);
        return ResponseEntity.ok().build();
    }

    /**
     * This endpoint deletes an existing Subscription object.
     *
     * @param id The UUID of the Subscription to delete.
     * @return ResponseEntity containing no content
     */
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSubscription(@RequestParam("id") UUID id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok().build();
    }
}
