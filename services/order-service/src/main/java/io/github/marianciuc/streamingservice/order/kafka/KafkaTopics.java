package io.github.marianciuc.streamingservice.order.kafka;

/**
 * This class provides constants for Kafka topics that are used in the application.
 */
public class KafkaTopics {
    public final static String UNSUBSCRIBE_USER_TOPIC = "unsubscribe-user-topic";
    public final static String ORDER_SUCCESSFULLY_COMPLETED_TOPIC = "order-successfully-completed-topic";
    public final static String RESOLUTION_CREATED_TOPIC = "resolution-created-topic";
    public final static String RESOLUTION_UPDATED_TOPIC = "resolution-updated-topic";
    public final static String RESOLUTION_DELETED_TOPIC = "resolution-deleted-topic";
}
