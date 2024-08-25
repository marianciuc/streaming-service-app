package com.mv.streamingservice.order.service;

import com.mv.streamingservice.order.client.UserClient;
import com.mv.streamingservice.order.client.SubscriptionClient;
import com.mv.streamingservice.order.dto.OrderRequest;
import com.mv.streamingservice.order.dto.OrderResponse;
import com.mv.streamingservice.order.dto.Subscription;
import com.mv.streamingservice.order.entity.Order;
import com.mv.streamingservice.order.entity.OrderStatus;
import com.mv.streamingservice.order.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient customerClient;
    private final SubscriptionClient subscriptionClient;
    private final KafkaProducer kafkaProducer;
    private final OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Subscription subscription = subscriptionClient.fetchSubscription(orderRequest.subscriptionId());
        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .orderDate(LocalDateTime.now())
                .subscriptionId(subscription.id())
                .customerId(orderRequest.userId())
                .amount(subscription.price())
                .build();
        OrderResponse orderResponse = orderMapper.toOrderResponse(orderRepository.save(order));
        kafkaProducer.produceOrderMessage(orderResponse);
        return orderResponse;
    }
}
