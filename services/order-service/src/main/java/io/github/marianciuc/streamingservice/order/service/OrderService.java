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
    /**
     * Creates a new order based on the provided order request and authentication.
     *
     * @param orderRequest the order request containing details for the new order
     * @param authentication the authentication context of the user creating the order
     * @return the response containing the details of the created order
     */
    OrderResponse createOrder(OrderRequest orderRequest, Authentication authentication);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param orderId the unique identifier of the order
     * @return the response containing the details of the order
     */
    OrderResponse getOrderById(UUID orderId);

    /**
     * Retrieves a list of orders made by a specific user.
     *
     * @param customerId the unique identifier of the user
     * @return a list of responses each containing details of an order
     */
    List<OrderResponse> getOrdersByUserId(UUID customerId);

    /**
     * Retrieves all orders in the system.
     *
     * @return a list of responses each containing details of an order
     */
    List<OrderResponse> getAllOrders();

    /**
     * Retrieves a list of orders based on the authentication context of the user.
     *
     * @param authentication the authentication context of the user
     * @return a list of responses each containing details of an order
     */
    List<OrderResponse> getOrdersByAuthentication(Authentication authentication);

    /**
     * Updates the status of an order based on its unique identifier and the new status.
     *
     * @param orderId the unique identifier of the order
     * @param orderStatus the new status to be applied to the order
     */
    void updateOrderStatus(UUID orderId, OrderStatus orderStatus);
}
