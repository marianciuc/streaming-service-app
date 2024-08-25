package com.mv.streamingservice.order.service;

import com.mv.streamingservice.order.dto.OrderResponse;
import com.mv.streamingservice.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getAmount(),
                order.getSubscriptionId(),
                order.getOrderDate(),
                order.getOrderStatus()
        );
    }
}
