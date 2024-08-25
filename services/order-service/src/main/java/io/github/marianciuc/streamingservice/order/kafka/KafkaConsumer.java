package io.github.marianciuc.streamingservice.order.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "payment-status", groupId = "payments")
    public void listenToPaymentStatusUpdate(String message) {
        System.out.println("Received message from topic1: " + message);
    }
}
