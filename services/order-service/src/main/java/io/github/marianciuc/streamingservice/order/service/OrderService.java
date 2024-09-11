/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: OrderService.java
 *
 */

package io.github.marianciuc.streamingservice.order.service;

import io.github.marianciuc.streamingservice.order.dto.OrderRequest;
import io.github.marianciuc.streamingservice.order.dto.OrderResponse;
import io.github.marianciuc.streamingservice.order.entity.OrderStatus;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

/**
 * OrderService provides methods for creating, retrieving, and updating orders.
 */
public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest, Authentication authentication);

    OrderResponse getOrderById(UUID orderId);

    List<OrderResponse> getOrdersByUserId(UUID customerId);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByAuthentication(Authentication authentication);

    void updateOrderStatus(UUID orderId, OrderStatus orderStatus);
}
