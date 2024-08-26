package io.github.marianciuc.streamingservice.subscription.kafka;

import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import io.github.marianciuc.streamingservice.subscription.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaUserSubscriptionConsumer {

    private final static String TOPIC = "unsubscribe-user";
    private final UserSubscriptionService userSubscriptionService;


    @KafkaListener(topics = TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTopic(UserSubscriptions userSubscriptions) {
      log.info("UserSubscriptionKafkaProducerService consumeTopic: {}", userSubscriptions);
      userSubscriptionService.deactivateSubscription(userSubscriptions);
    }
}
