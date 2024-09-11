/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: OrderServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.order.service.impl;

import io.github.marianciuc.jwtsecurity.entity.JwtUser;
import io.github.marianciuc.jwtsecurity.service.UserService;
import io.github.marianciuc.streamingservice.order.client.SubscriptionClient;
import io.github.marianciuc.streamingservice.order.dto.*;
import io.github.marianciuc.streamingservice.order.entity.Order;
import io.github.marianciuc.streamingservice.order.entity.OrderStatus;
import io.github.marianciuc.streamingservice.order.kafka.KafkaProducer;
import io.github.marianciuc.streamingservice.order.repository.OrderRepository;
import io.github.marianciuc.streamingservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of the OrderService.
 * @author Vladimir Marianciuc
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final SubscriptionClient subscriptionClient;
    private final KafkaProducer kafkaProducer;
    private final UserService userService;

    /**
     * Creates a new order based on the provided order request and authentication details.
     *
     * @param orderRequest    the OrderRequest containing details of the order to be created.
     * @param authentication  the Authentication object containing the user's authentication details.
     * @return the created OrderResponse.
     * @throws RuntimeException if there is an issue with the subscription.
     */
//    @Override
//    public OrderResponse createOrder(OrderRequest orderRequest) {
////        ResponseEntity<SubscriptionDto> subscriptionResponse = subscriptionClient.getSubscription(orderRequest.subscriptionId());
////        ResponseEntity<UserSubscriptionDto> activeSubscriptionResponse = subscriptionClient.getActiveSubscription(userId);
////
////        if (subscriptionResponse.getStatusCode().is2xxSuccessful()) {
////            this.cancelExistingOrders(userId);
////            if (activeSubscriptionResponse.getStatusCode().is4xxClientError()) {
////                return this.createNewOrder(subscriptionResponse.getBody(), userId, orderRequest.subscriptionId(), OrderStatus.CREATED, null);
////            } else if (activeSubscriptionResponse.getStatusCode().is2xxSuccessful() && activeSubscriptionResponse.getBody() != null) {
////                UUID activeSubscriptionId = activeSubscriptionResponse.getBody().id();
////                OrderResponse orderResponse = this.createNewOrder(subscriptionResponse.getBody(), userId, orderRequest.subscriptionId(), OrderStatus.SCHEDULED, activeSubscriptionResponse.getBody().endDate());
////                subscriptionClient.cancelSubscription(activeSubscriptionId);
////                return orderResponse;
////            }
////        }
//
//        throw new RuntimeException("Unable to create order due to subscription issues");
//    }

    /**
     * Resolves the user ID based on the authentication details and request.
     *
     * @param authentication the Authentication object containing the user's authentication details.
     * @param orderRequest   the OrderRequest containing details of the order to be created.
     * @return the resolved user ID.
     */
    private UUID resolveUserId(Authentication authentication, OrderRequest orderRequest) {
        if (userService.getUser().isService()) {
            return orderRequest.userId();
        } else {
            return ((JwtUser) authentication.getPrincipal()).getId();
        }
    }

    /**
     * Cancels any existing orders for the user that are in the CREATED status.
     *
     * @param userId the ID of the user whose orders are to be cancelled.
     */
    private void cancelExistingOrders(UUID userId) {
        List<Order> orders = repository.findAllByUserIdAndOrderStatus(userId, OrderStatus.CREATED);
        orders.forEach(order -> order.setOrderStatus(OrderStatus.CANCELLED));
        repository.saveAll(orders);
    }

    /**
     * Creates a new order.
     *
     * @param subscriptionDto the SubscriptionDto containing subscription details.
     * @param userId          the ID of the user.
     * @param subscriptionId  the ID of the subscription.
     * @param orderStatus     the status of the order.
     * @param scheduledTime   the scheduled time for the order.
     * @return the created OrderResponse.
     */
    private OrderResponse createNewOrder(SubscriptionDto subscriptionDto, UUID userId, UUID subscriptionId, OrderStatus orderStatus, LocalDate scheduledTime) {
//        Order.OrderBuilder orderBuilder = Order.builder()
//                .orderCreateDate(LocalDateTime.now())
//                .subscriptionId(subscriptionId)
//                .userId(userId)
//                .amount(Objects.requireNonNull(subscriptionDto).price())
//                .orderStatus(orderStatus);
//
//        if (scheduledTime != null) {
//            orderBuilder.scheduledTime(scheduledTime);
//        }
//
//        Order order = orderBuilder.build();
//        kafkaProducer.produceOrderMessage(orderResponse);
//        return orderResponse;
        return null;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, Authentication authentication) {
        return null;
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        return null;
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(UUID customerId) {
        return List.of();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }

    @Override
    public List<OrderResponse> getOrdersByAuthentication(Authentication authentication) {
        return List.of();
    }

    @Override
    public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {

    }
}
