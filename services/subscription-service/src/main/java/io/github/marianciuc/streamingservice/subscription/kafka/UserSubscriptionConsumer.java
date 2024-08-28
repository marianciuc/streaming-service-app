package io.github.marianciuc.streamingservice.subscription.kafka;

import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import io.github.marianciuc.streamingservice.subscription.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static io.github.marianciuc.streamingservice.subscription.kafka.KafkaTopics.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserSubscriptionConsumer {

    private final UserSubscriptionService userSubscriptionService;

    @KafkaListener(topics = UNSUBSCRIBE_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeUserSubscription(UserSubscriptions userSubscriptions) {
        log.info("Kafka consumer consumeUserSubscription: {}", userSubscriptions);
        userSubscriptionService.unsubscribeUser(userSubscriptions);
    }
}
