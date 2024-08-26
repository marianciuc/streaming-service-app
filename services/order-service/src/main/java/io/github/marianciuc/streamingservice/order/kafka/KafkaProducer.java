package io.github.marianciuc.streamingservice.order.kafka;


import io.github.marianciuc.streamingservice.order.dto.CreateOrderMessage;
import io.github.marianciuc.streamingservice.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, CreateOrderMessage> kafkaTemplate;

    public void produceOrderMessage(OrderResponse order) {
    }
}
