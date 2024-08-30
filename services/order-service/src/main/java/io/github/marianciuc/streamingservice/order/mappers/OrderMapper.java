package io.github.marianciuc.streamingservice.order.mappers;

import io.github.marianciuc.streamingservice.order.dto.OrderResponse;
import io.github.marianciuc.streamingservice.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getAmount(),
                order.getSubscriptionId(),
                order.getOrderCreateDate(),
                order.getOrderStatus()
        );
    }
}
