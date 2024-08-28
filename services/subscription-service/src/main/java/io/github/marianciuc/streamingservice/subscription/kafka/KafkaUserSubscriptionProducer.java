package io.github.marianciuc.streamingservice.subscription.kafka;

import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static io.github.marianciuc.streamingservice.subscription.kafka.KafkaTopics.UNSUBSCRIBE_USER_TOPIC;

/**
 * KafkaUserSubscriptionProducer is a service class that is responsible for sending user subscriptions to a Kafka topic.
 */
@Service
public class KafkaUserSubscriptionProducer {

    private KafkaTemplate<String, UserSubscriptions> kafkaTemplate;

    public void sendTopic(UserSubscriptions userSubscriptions) {
        kafkaTemplate.send(UNSUBSCRIBE_USER_TOPIC, userSubscriptions);
    }
}
