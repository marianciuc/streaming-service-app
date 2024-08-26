package io.github.marianciuc.streamingservice.order.kafka;

import io.github.marianciuc.streamingservice.order.dto.CreateOrderMessage;
import io.github.marianciuc.streamingservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "payment-status", groupId = "payments")
    public void listenToPaymentStatusUpdate(String message) {
        System.out.println("Received message from topic1: " + message);
    }

    @KafkaListener(topics = "subscribe-user", groupId = "subscription")
    public void listenToSubscription(CreateOrderMessage message) {
        orderService.createOrder(message);
        System.out.println("Received message from topic1: " + message);
    }
}
