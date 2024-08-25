package com.mv.streamingservice.order.kafka;

import com.mv.streamingservice.order.dto.OrderMessage;
import com.mv.streamingservice.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void produceOrderMessage(OrderResponse order) {
    }
}
