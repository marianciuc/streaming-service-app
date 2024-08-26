package io.github.marianciuc.streamingservice.order.controller;

import io.github.marianciuc.streamingservice.order.dto.OrderRequest;
import io.github.marianciuc.streamingservice.order.dto.OrderResponse;
import io.github.marianciuc.streamingservice.order.entity.Order;
import io.github.marianciuc.streamingservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SERVICE','ROLE_UNSUBSCRIDED_USER')")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    public ResponseEntity<UUID> updatePlan(@RequestBody OrderRequest orderRequest) {
        return null;
    }

    @GetMapping
    public ResponseEntity<OrderResponse> getOrder(@RequestParam UUID id) {
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SERVICE')")
    public ResponseEntity<Void> updateOrderStatus(@RequestBody Order order) {
        return null;
    }
}
