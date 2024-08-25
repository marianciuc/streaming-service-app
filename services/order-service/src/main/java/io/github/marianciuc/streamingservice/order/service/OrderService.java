package io.github.marianciuc.streamingservice.order.service;

import io.github.marianciuc.streamingservice.order.client.SubscriptionClient;
import io.github.marianciuc.streamingservice.order.dto.OrderRequest;
import io.github.marianciuc.streamingservice.order.dto.OrderResponse;
import io.github.marianciuc.streamingservice.order.dto.Subscription;
import io.github.marianciuc.streamingservice.order.entity.Order;
import io.github.marianciuc.streamingservice.order.entity.OrderStatus;
import io.github.marianciuc.streamingservice.order.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
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
