package io.github.marianciuc.streamingservice.order.service;

import io.github.marianciuc.jwtsecurity.service.UserService;
import io.github.marianciuc.streamingservice.order.client.SubscriptionClient;
import io.github.marianciuc.streamingservice.order.dto.CreateOrderMessage;
import io.github.marianciuc.streamingservice.order.dto.OrderRequest;
import io.github.marianciuc.streamingservice.order.dto.OrderResponse;
import io.github.marianciuc.streamingservice.order.dto.Subscription;
import io.github.marianciuc.streamingservice.order.entity.Order;
import io.github.marianciuc.streamingservice.order.entity.OrderStatus;
import io.github.marianciuc.streamingservice.order.kafka.KafkaProducer;
import io.github.marianciuc.streamingservice.order.mappers.OrderMapper;
import io.github.marianciuc.streamingservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SubscriptionClient subscriptionClient;
    private final KafkaProducer kafkaProducer;
    private final OrderMapper orderMapper;
    private final UserService userService;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Subscription subscription = subscriptionClient.fetchSubscription(orderRequest.subscriptionId());
        UUID userId = userService.getUserId();
        // Проверить есть ли у пользователя акивная подписка
        Optional<Subscription> activeSubscription = subscriptionClient.fetchActiveUserSubscription(userId);

        if (activeSubscription.isPresent()) {

        }


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

    public void createOrder(CreateOrderMessage message) {
        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .orderDate(LocalDateTime.now())
                .subscriptionId(message.subscriptionId())
                .customerId(message.userId())
                .amount(message.price())
                .build();
        OrderResponse orderResponse = orderMapper.toOrderResponse(orderRepository.save(order));
        kafkaProducer.produceOrderMessage(orderResponse);
    }
}
