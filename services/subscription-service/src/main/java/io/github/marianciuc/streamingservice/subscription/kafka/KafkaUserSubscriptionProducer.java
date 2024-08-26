package io.github.marianciuc.streamingservice.subscription.kafka;

import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * UserSubscriptionKafkaConsumerService is a service class that is responsible for sending user subscriptions to a Kafka topic.
 */
@Service
public class KafkaUserSubscriptionProducer {

    private final static String UNSUBSCRIBE_USER_TOPIC = "unsubscribe-user";

    private KafkaTemplate<String, UserSubscriptions> kafkaTemplate;

    public void sendTopic(UserSubscriptions userSubscriptions) {
        kafkaTemplate.send(UNSUBSCRIBE_USER_TOPIC, userSubscriptions);
    }
}
