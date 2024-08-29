package io.github.marianciuc.streamingservice.subscription.kafka;

import io.github.marianciuc.streamingservice.subscription.dto.OrderCreationEventKafkaDto;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import io.github.marianciuc.streamingservice.subscription.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;

import static io.github.marianciuc.streamingservice.subscription.kafka.KafkaTopics.ORDER_SUCCESSFULLY_COMPLETED_TOPIC;
import static io.github.marianciuc.streamingservice.subscription.kafka.KafkaTopics.UNSUBSCRIBE_USER_TOPIC;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderTopic {

    private final UserSubscriptionService userSubscriptionService;

    @KafkaListener(topics = ORDER_SUCCESSFULLY_COMPLETED_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeOrderTopic(OrderCreationEventKafkaDto dto) {
        log.info("Kafka consumer consumeOrderTopic: {}", dto);
        userSubscriptionService.subscribeUser(dto);
    }
}
