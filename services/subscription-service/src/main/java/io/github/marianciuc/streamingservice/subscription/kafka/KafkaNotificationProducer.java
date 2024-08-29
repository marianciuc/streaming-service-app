package io.github.marianciuc.streamingservice.subscription.kafka;

import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaNotificationProducer {
    private final static String UNSUBSCRIBE_USER_TOPIC = "subscription-canceled";

    private KafkaTemplate<String, UserSubscriptions> kafkaTemplate;

    public void sendTopic(UserSubscriptions userSubscriptions) {
        kafkaTemplate.send(UNSUBSCRIBE_USER_TOPIC, userSubscriptions);
    }
}
